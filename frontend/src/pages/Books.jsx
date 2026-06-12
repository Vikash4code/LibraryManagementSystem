import { useEffect, useState } from "react";
import client from "../api/client";
import BookCard from "../components/BookCard";
import Pagination from "../components/Pagination";
import Spinner from "../components/Spinner";

export default function Books() {
  const [books, setBooks] = useState([]);
  const [categories, setCategories] = useState([]);
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    client.get("/books/categories").then(({ data }) => setCategories(data));
  }, []);

  useEffect(() => {
    setLoading(true);
    client
      .get("/books", { params: { search, category, page, size: 12 } })
      .then(({ data }) => {
        setBooks(data.content);
        setTotalPages(data.totalPages);
      })
      .finally(() => setLoading(false));
  }, [search, category, page]);

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800">Browse Books</h1>

      <div className="mt-4 flex flex-col sm:flex-row gap-3">
        <input
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
            setPage(0);
          }}
          placeholder="Search by title or author..."
          className="flex-1 rounded-md border border-gray-300 px-4 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
        <select
          value={category}
          onChange={(e) => {
            setCategory(e.target.value);
            setPage(0);
          }}
          className="rounded-md border border-gray-300 px-4 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
        >
          <option value="">All categories</option>
          {categories.map((c) => (
            <option key={c} value={c}>{c}</option>
          ))}
        </select>
      </div>

      {loading ? (
        <Spinner />
      ) : books.length === 0 ? (
        <p className="text-center text-gray-500 py-16">No books found.</p>
      ) : (
        <div className="mt-6 grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-5">
          {books.map((book) => (
            <BookCard key={book.id} book={book} />
          ))}
        </div>
      )}

      <Pagination page={page} totalPages={totalPages} onChange={setPage} />
    </div>
  );
}
