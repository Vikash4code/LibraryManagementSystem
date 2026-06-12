import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import client from "../api/client";
import Spinner from "../components/Spinner";
import { useAuth } from "../context/AuthContext";

export default function BookDetail() {
  const { id } = useParams();
  const { user, isAdmin } = useAuth();
  const [book, setBook] = useState(null);
  const [message, setMessage] = useState(null); // { type: "ok" | "error", text }

  useEffect(() => {
    client.get(`/books/${id}`).then(({ data }) => setBook(data));
  }, [id]);

  const showMessage = (type, text) => setMessage({ type, text });

  const handleReserve = async () => {
    try {
      await client.post("/reservations", { bookId: book.id });
      showMessage("ok", "Book reserved! We will notify you when a copy is back.");
    } catch (err) {
      showMessage("error", err.response?.data?.message || "Could not reserve the book");
    }
  };

  const handleWishlist = async () => {
    try {
      await client.post("/wishlist", { bookId: book.id });
      showMessage("ok", "Added to your wishlist.");
    } catch (err) {
      showMessage("error", err.response?.data?.message || "Could not add to wishlist");
    }
  };

  if (!book) return <Spinner />;

  const available = book.availableCopies > 0;

  return (
    <div className="max-w-4xl mx-auto px-4 py-10">
      <div className="bg-white rounded-xl shadow-md p-6 sm:p-8 flex flex-col sm:flex-row gap-8">
        <div className="w-48 h-64 bg-gray-100 rounded-lg flex items-center justify-center shrink-0 mx-auto sm:mx-0">
          {book.coverImageUrl ? (
            <img
              src={book.coverImageUrl}
              alt={book.title}
              className="h-full object-contain rounded-lg"
              onError={(e) => (e.target.style.display = "none")}
            />
          ) : (
            <span className="text-5xl">📖</span>
          )}
        </div>

        <div className="flex-1">
          <h1 className="text-2xl font-bold text-gray-800">{book.title}</h1>
          <p className="text-gray-500 mt-1">by {book.author}</p>

          <div className="flex flex-wrap gap-2 mt-3">
            {book.category && (
              <span className="text-xs bg-indigo-50 text-indigo-700 px-2 py-1 rounded-full">
                {book.category}
              </span>
            )}
            {book.isbn && (
              <span className="text-xs bg-gray-100 text-gray-600 px-2 py-1 rounded-full">
                ISBN {book.isbn}
              </span>
            )}
            <span
              className={`text-xs px-2 py-1 rounded-full font-medium ${
                available ? "bg-green-50 text-green-700" : "bg-red-50 text-red-600"
              }`}
            >
              {available
                ? `${book.availableCopies} of ${book.totalCopies} copies available`
                : "Currently out of stock"}
            </span>
          </div>

          {book.description && (
            <p className="text-gray-600 mt-4 leading-relaxed">{book.description}</p>
          )}

          {message && (
            <div
              className={`mt-4 text-sm rounded-md px-4 py-2 ${
                message.type === "ok"
                  ? "bg-green-50 text-green-700"
                  : "bg-red-50 text-red-700"
              }`}
            >
              {message.text}
            </div>
          )}

          {user && !isAdmin && (
            <div className="flex gap-3 mt-6">
              {!available && (
                <button
                  onClick={handleReserve}
                  className="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700"
                >
                  Reserve this book
                </button>
              )}
              <button
                onClick={handleWishlist}
                className="border border-indigo-600 text-indigo-600 px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-50"
              >
                ♡ Add to wishlist
              </button>
            </div>
          )}
          {available && user && !isAdmin && (
            <p className="text-xs text-gray-400 mt-3">
              Visit the library desk to borrow this book.
            </p>
          )}
        </div>
      </div>
    </div>
  );
}
