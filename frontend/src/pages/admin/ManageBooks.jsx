import { useEffect, useState } from "react";
import client from "../../api/client";
import Pagination from "../../components/Pagination";
import Spinner from "../../components/Spinner";

const emptyForm = {
  title: "", author: "", isbn: "", category: "",
  totalCopies: 1, coverImageUrl: "", description: "",
};

export default function ManageBooks() {
  const [books, setBooks] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [search, setSearch] = useState("");
  const [editing, setEditing] = useState(null); // null = closed, "new" or a book object
  const [form, setForm] = useState(emptyForm);
  const [error, setError] = useState("");

  const load = () =>
    client.get("/books", { params: { search, page, size: 10 } }).then(({ data }) => {
      setBooks(data.content);
      setTotalPages(data.totalPages);
    });

  useEffect(() => {
    load();
  }, [page, search]);

  const openNew = () => {
    setForm(emptyForm);
    setError("");
    setEditing("new");
  };

  const openEdit = (book) => {
    setForm({ ...book, coverImageUrl: book.coverImageUrl || "", description: book.description || "", isbn: book.isbn || "", category: book.category || "" });
    setError("");
    setEditing(book);
  };

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      if (editing === "new") {
        await client.post("/books", { ...form, totalCopies: Number(form.totalCopies) });
      } else {
        await client.put(`/books/${editing.id}`, { ...form, totalCopies: Number(form.totalCopies) });
      }
      setEditing(null);
      load();
    } catch (err) {
      const data = err.response?.data;
      const fieldError = data?.fieldErrors && Object.values(data.fieldErrors)[0];
      setError(fieldError || data?.message || "Could not save the book");
    }
  };

  const handleDelete = async (book) => {
    if (!window.confirm(`Delete "${book.title}"?`)) return;
    try {
      await client.delete(`/books/${book.id}`);
      load();
    } catch (err) {
      alert(err.response?.data?.message || "Could not delete the book");
    }
  };

  if (!books) return <Spinner />;

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-800">Manage Books</h1>
        <button
          onClick={openNew}
          className="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700"
        >
          + Add Book
        </button>
      </div>

      <input
        value={search}
        onChange={(e) => {
          setSearch(e.target.value);
          setPage(0);
        }}
        placeholder="Search books..."
        className="mt-4 w-full sm:w-80 rounded-md border border-gray-300 px-4 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
      />

      <div className="mt-4 bg-white rounded-lg shadow overflow-x-auto">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 text-gray-600 text-left">
            <tr>
              <th className="px-4 py-3">Title</th>
              <th className="px-4 py-3">Author</th>
              <th className="px-4 py-3">Category</th>
              <th className="px-4 py-3">Copies</th>
              <th className="px-4 py-3">Available</th>
              <th className="px-4 py-3"></th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {books.map((book) => (
              <tr key={book.id}>
                <td className="px-4 py-3 font-medium text-gray-800">{book.title}</td>
                <td className="px-4 py-3 text-gray-600">{book.author}</td>
                <td className="px-4 py-3 text-gray-600">{book.category || "—"}</td>
                <td className="px-4 py-3 text-gray-600">{book.totalCopies}</td>
                <td className="px-4 py-3 text-gray-600">{book.availableCopies}</td>
                <td className="px-4 py-3 text-right whitespace-nowrap">
                  <button
                    onClick={() => openEdit(book)}
                    className="text-indigo-600 hover:underline mr-3"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(book)}
                    className="text-red-600 hover:underline"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <Pagination page={page} totalPages={totalPages} onChange={setPage} />

      {editing && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-xl shadow-xl w-full max-w-lg p-6 max-h-[90vh] overflow-y-auto">
            <h2 className="text-lg font-bold text-gray-800">
              {editing === "new" ? "Add Book" : "Edit Book"}
            </h2>

            {error && (
              <div className="mt-3 bg-red-50 text-red-700 text-sm rounded-md px-4 py-2">
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit} className="mt-4 space-y-3">
              {[
                { name: "title", label: "Title", required: true },
                { name: "author", label: "Author", required: true },
                { name: "isbn", label: "ISBN" },
                { name: "category", label: "Category" },
                { name: "coverImageUrl", label: "Cover image URL" },
              ].map((field) => (
                <div key={field.name}>
                  <label className="block text-sm font-medium text-gray-700">{field.label}</label>
                  <input
                    name={field.name}
                    required={field.required}
                    value={form[field.name]}
                    onChange={handleChange}
                    className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  />
                </div>
              ))}
              <div>
                <label className="block text-sm font-medium text-gray-700">Total copies</label>
                <input
                  name="totalCopies"
                  type="number"
                  min={1}
                  required
                  value={form.totalCopies}
                  onChange={handleChange}
                  className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Description</label>
                <textarea
                  name="description"
                  rows={3}
                  value={form.description}
                  onChange={handleChange}
                  className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                />
              </div>
              <div className="flex justify-end gap-3 pt-2">
                <button
                  type="button"
                  onClick={() => setEditing(null)}
                  className="px-4 py-2 text-sm rounded-md border border-gray-300 hover:bg-gray-50"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 text-sm rounded-md bg-indigo-600 text-white font-medium hover:bg-indigo-700"
                >
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
