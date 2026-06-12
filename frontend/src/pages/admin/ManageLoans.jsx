import { useEffect, useState } from "react";
import client from "../../api/client";
import Pagination from "../../components/Pagination";
import Spinner from "../../components/Spinner";

export default function ManageLoans() {
  const [loans, setLoans] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [statusFilter, setStatusFilter] = useState("");
  const [showIssue, setShowIssue] = useState(false);
  const [users, setUsers] = useState([]);
  const [books, setBooks] = useState([]);
  const [issueForm, setIssueForm] = useState({ userId: "", bookId: "" });
  const [error, setError] = useState("");

  const load = () =>
    client
      .get("/loans", { params: { status: statusFilter || undefined, page, size: 10, sort: "id,desc" } })
      .then(({ data }) => {
        setLoans(data.content);
        setTotalPages(data.totalPages);
      });

  useEffect(() => {
    load();
  }, [page, statusFilter]);

  const openIssueModal = async () => {
    setError("");
    setIssueForm({ userId: "", bookId: "" });
    const [usersRes, booksRes] = await Promise.all([
      client.get("/users"),
      client.get("/books", { params: { size: 200 } }),
    ]);
    setUsers(usersRes.data.filter((u) => u.role === "USER"));
    setBooks(booksRes.data.content.filter((b) => b.availableCopies > 0));
    setShowIssue(true);
  };

  const handleIssue = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await client.post("/loans", {
        userId: Number(issueForm.userId),
        bookId: Number(issueForm.bookId),
      });
      setShowIssue(false);
      load();
    } catch (err) {
      setError(err.response?.data?.message || "Could not issue the book");
    }
  };

  const handleReturn = async (loan) => {
    if (!window.confirm(`Mark "${loan.bookTitle}" as returned?`)) return;
    try {
      const { data } = await client.put(`/loans/${loan.id}/return`);
      if (data.fineAmount > 0) {
        alert(`Book returned late — fine of ₹${data.fineAmount} applied.`);
      }
      load();
    } catch (err) {
      alert(err.response?.data?.message || "Could not return the book");
    }
  };

  if (!loans) return <Spinner />;

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-800">Loans</h1>
        <button
          onClick={openIssueModal}
          className="bg-indigo-600 text-white px-4 py-2 rounded-md text-sm font-medium hover:bg-indigo-700"
        >
          + Issue Book
        </button>
      </div>

      <select
        value={statusFilter}
        onChange={(e) => {
          setStatusFilter(e.target.value);
          setPage(0);
        }}
        className="mt-4 rounded-md border border-gray-300 px-4 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
      >
        <option value="">All loans</option>
        <option value="ISSUED">Issued</option>
        <option value="RETURNED">Returned</option>
      </select>

      <div className="mt-4 bg-white rounded-lg shadow overflow-x-auto">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 text-gray-600 text-left">
            <tr>
              <th className="px-4 py-3">Member</th>
              <th className="px-4 py-3">Book</th>
              <th className="px-4 py-3">Issued</th>
              <th className="px-4 py-3">Due</th>
              <th className="px-4 py-3">Fine</th>
              <th className="px-4 py-3">Status</th>
              <th className="px-4 py-3"></th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {loans.map((loan) => (
              <tr key={loan.id}>
                <td className="px-4 py-3 text-gray-800">{loan.userName}</td>
                <td className="px-4 py-3 font-medium text-gray-800">{loan.bookTitle}</td>
                <td className="px-4 py-3 text-gray-600">{loan.issueDate}</td>
                <td className="px-4 py-3 text-gray-600">{loan.dueDate}</td>
                <td className="px-4 py-3 text-gray-600">
                  {loan.fineAmount > 0 ? `₹${loan.fineAmount}` : "—"}
                </td>
                <td className="px-4 py-3">
                  <span
                    className={`text-xs px-2 py-1 rounded-full ${
                      loan.status === "RETURNED"
                        ? "bg-green-50 text-green-700"
                        : new Date(loan.dueDate) < new Date()
                        ? "bg-red-50 text-red-600"
                        : "bg-amber-50 text-amber-700"
                    }`}
                  >
                    {loan.status === "ISSUED" && new Date(loan.dueDate) < new Date()
                      ? "OVERDUE"
                      : loan.status}
                  </span>
                </td>
                <td className="px-4 py-3 text-right">
                  {loan.status === "ISSUED" && (
                    <button
                      onClick={() => handleReturn(loan)}
                      className="text-indigo-600 hover:underline"
                    >
                      Return
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <Pagination page={page} totalPages={totalPages} onChange={setPage} />

      {showIssue && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-xl shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold text-gray-800">Issue Book</h2>

            {error && (
              <div className="mt-3 bg-red-50 text-red-700 text-sm rounded-md px-4 py-2">
                {error}
              </div>
            )}

            <form onSubmit={handleIssue} className="mt-4 space-y-3">
              <div>
                <label className="block text-sm font-medium text-gray-700">Member</label>
                <select
                  required
                  value={issueForm.userId}
                  onChange={(e) => setIssueForm({ ...issueForm, userId: e.target.value })}
                  className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                >
                  <option value="">Select member...</option>
                  {users.map((u) => (
                    <option key={u.id} value={u.id}>
                      {u.name} ({u.email})
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Book</label>
                <select
                  required
                  value={issueForm.bookId}
                  onChange={(e) => setIssueForm({ ...issueForm, bookId: e.target.value })}
                  className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                >
                  <option value="">Select book...</option>
                  {books.map((b) => (
                    <option key={b.id} value={b.id}>
                      {b.title} ({b.availableCopies} available)
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex justify-end gap-3 pt-2">
                <button
                  type="button"
                  onClick={() => setShowIssue(false)}
                  className="px-4 py-2 text-sm rounded-md border border-gray-300 hover:bg-gray-50"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 text-sm rounded-md bg-indigo-600 text-white font-medium hover:bg-indigo-700"
                >
                  Issue (due in 7 days)
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
