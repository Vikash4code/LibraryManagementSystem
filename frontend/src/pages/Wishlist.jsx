import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import client from "../api/client";
import Spinner from "../components/Spinner";

export default function Wishlist() {
  const [books, setBooks] = useState(null);

  const load = () => client.get("/wishlist").then(({ data }) => setBooks(data));

  useEffect(() => {
    load();
  }, []);

  const handleRemove = async (bookId) => {
    await client.delete(`/wishlist/${bookId}`);
    load();
  };

  if (!books) return <Spinner />;

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800">My Wishlist</h1>

      {books.length === 0 ? (
        <p className="text-center text-gray-500 py-16">
          Your wishlist is empty. Browse books and add the ones you like.
        </p>
      ) : (
        <div className="mt-6 space-y-3">
          {books.map((book) => (
            <div
              key={book.id}
              className="bg-white rounded-lg shadow p-4 flex items-center gap-4"
            >
              <div className="h-16 w-12 bg-gray-100 rounded flex items-center justify-center shrink-0">
                {book.coverImageUrl ? (
                  <img
                    src={book.coverImageUrl}
                    alt={book.title}
                    className="h-full object-contain rounded"
                    onError={(e) => (e.target.style.display = "none")}
                  />
                ) : (
                  <span>📖</span>
                )}
              </div>
              <div className="flex-1">
                <Link
                  to={`/books/${book.id}`}
                  className="font-medium text-gray-800 hover:text-indigo-600"
                >
                  {book.title}
                </Link>
                <p className="text-sm text-gray-500">{book.author}</p>
              </div>
              <span
                className={`text-xs font-medium ${
                  book.availableCopies > 0 ? "text-green-600" : "text-red-500"
                }`}
              >
                {book.availableCopies > 0 ? "Available" : "Out of stock"}
              </span>
              <button
                onClick={() => handleRemove(book.id)}
                className="text-sm text-red-600 hover:underline"
              >
                Remove
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
