import { Link } from "react-router-dom";

export default function BookCard({ book }) {
  return (
    <Link
      to={`/books/${book.id}`}
      className="bg-white rounded-lg shadow hover:shadow-md transition-shadow overflow-hidden flex flex-col"
    >
      <div className="h-48 bg-gray-100 flex items-center justify-center">
        {book.coverImageUrl ? (
          <img
            src={book.coverImageUrl}
            alt={book.title}
            className="h-full object-contain"
            onError={(e) => (e.target.style.display = "none")}
          />
        ) : (
          <span className="text-4xl">📖</span>
        )}
      </div>
      <div className="p-4 flex-1 flex flex-col">
        <h3 className="font-semibold text-gray-800 line-clamp-2">{book.title}</h3>
        <p className="text-sm text-gray-500 mt-1">{book.author}</p>
        <div className="mt-auto pt-3 flex items-center justify-between">
          {book.category && (
            <span className="text-xs bg-indigo-50 text-indigo-700 px-2 py-1 rounded-full">
              {book.category}
            </span>
          )}
          <span
            className={`text-xs font-medium ${
              book.availableCopies > 0 ? "text-green-600" : "text-red-500"
            }`}
          >
            {book.availableCopies > 0
              ? `${book.availableCopies} available`
              : "Out of stock"}
          </span>
        </div>
      </div>
    </Link>
  );
}
