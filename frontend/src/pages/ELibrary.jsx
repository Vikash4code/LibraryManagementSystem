import { useEffect, useState } from "react";
import client from "../api/client";
import Spinner from "../components/Spinner";

const TOPICS = ["Fiction", "Science", "History", "Adventure", "Philosophy", "Poetry"];

export default function ELibrary() {
  const [query, setQuery] = useState("");
  const [topic, setTopic] = useState("");
  const [page, setPage] = useState(1);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(true);

  // Open Library paginates by page number starting at 1
  useEffect(() => {
    setLoading(true);
    client
      .get("/elibrary/search", { params: { q: query, topic, page } })
      .then(({ data }) => setResult(data))
      .finally(() => setLoading(false));
  }, [query, topic, page]);

  const handleSearch = (e) => {
    e.preventDefault();
    setQuery(e.target.elements.q.value);
    setTopic("");
    setPage(1);
  };

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800">E-Library</h1>
      <p className="text-gray-500 mt-1 text-sm">
        Thousands of free books from Open Library and the Internet Archive — read online or download, no account needed.
      </p>

      <form onSubmit={handleSearch} className="mt-4 flex gap-3">
        <input
          name="q"
          defaultValue={query}
          placeholder="Search free books, e.g. Sherlock Holmes..."
          className="flex-1 rounded-md border border-gray-300 px-4 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
        <button
          type="submit"
          className="bg-indigo-600 text-white px-5 py-2 rounded-md text-sm font-medium hover:bg-indigo-700"
        >
          Search
        </button>
      </form>

      <div className="mt-3 flex flex-wrap gap-2">
        {TOPICS.map((t) => (
          <button
            key={t}
            onClick={() => {
              setTopic(t === topic ? "" : t);
              setQuery("");
              setPage(1);
            }}
            className={`text-xs px-3 py-1.5 rounded-full border ${
              topic === t
                ? "bg-indigo-600 text-white border-indigo-600"
                : "bg-white text-gray-600 border-gray-300 hover:border-indigo-400"
            }`}
          >
            {t}
          </button>
        ))}
      </div>

      {loading ? (
        <Spinner />
      ) : (
        <>
          <div className="mt-6 grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-5">
            {result?.books.map((book) => (
              <div
                key={book.id}
                className="bg-white rounded-lg shadow overflow-hidden flex flex-col"
              >
                <div className="h-48 bg-gray-100 flex items-center justify-center">
                  {book.coverUrl ? (
                    <img src={book.coverUrl} alt={book.title} className="h-full object-contain" />
                  ) : (
                    <span className="text-4xl">📜</span>
                  )}
                </div>
                <div className="p-4 flex-1 flex flex-col">
                  <h3 className="font-semibold text-gray-800 text-sm line-clamp-2">{book.title}</h3>
                  <p className="text-xs text-gray-500 mt-1 line-clamp-1">{book.authors}</p>
                  <div className="mt-auto pt-3 flex gap-2">
                    {book.readOnlineUrl && (
                      <a
                        href={book.readOnlineUrl}
                        target="_blank"
                        rel="noreferrer"
                        className="flex-1 text-center bg-indigo-600 text-white text-xs px-2 py-1.5 rounded-md hover:bg-indigo-700"
                      >
                        Read Online
                      </a>
                    )}
                    {book.downloadUrl && (
                      <a
                        href={book.downloadUrl}
                        className="flex-1 text-center border border-indigo-600 text-indigo-600 text-xs px-2 py-1.5 rounded-md hover:bg-indigo-50"
                      >
                        Download
                      </a>
                    )}
                  </div>
                </div>
              </div>
            ))}
          </div>

          <div className="flex items-center justify-center gap-2 mt-6">
            <button
              disabled={page === 1}
              onClick={() => setPage(page - 1)}
              className="px-3 py-1.5 text-sm rounded-md border border-gray-300 bg-white disabled:opacity-40 hover:bg-gray-50"
            >
              Previous
            </button>
            <span className="text-sm text-gray-600">Page {page}</span>
            <button
              disabled={!result?.hasNext}
              onClick={() => setPage(page + 1)}
              className="px-3 py-1.5 text-sm rounded-md border border-gray-300 bg-white disabled:opacity-40 hover:bg-gray-50"
            >
              Next
            </button>
          </div>
        </>
      )}
    </div>
  );
}
