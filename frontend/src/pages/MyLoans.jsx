import { useEffect, useState } from "react";
import client from "../api/client";
import Pagination from "../components/Pagination";
import Spinner from "../components/Spinner";

const statusBadge = (loan) => {
  if (loan.status === "RETURNED") {
    return <span className="text-xs bg-green-50 text-green-700 px-2 py-1 rounded-full">Returned</span>;
  }
  const overdue = new Date(loan.dueDate) < new Date();
  return overdue ? (
    <span className="text-xs bg-red-50 text-red-600 px-2 py-1 rounded-full">Overdue</span>
  ) : (
    <span className="text-xs bg-amber-50 text-amber-700 px-2 py-1 rounded-full">Issued</span>
  );
};

export default function MyLoans() {
  const [loans, setLoans] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    client.get("/loans/my", { params: { page, size: 10 } }).then(({ data }) => {
      setLoans(data.content);
      setTotalPages(data.totalPages);
    });
  }, [page]);

  if (!loans) return <Spinner />;

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800">My Borrowing History</h1>

      {loans.length === 0 ? (
        <p className="text-center text-gray-500 py-16">You have not borrowed any books yet.</p>
      ) : (
        <div className="mt-6 bg-white rounded-lg shadow overflow-x-auto">
          <table className="w-full text-sm">
            <thead className="bg-gray-50 text-gray-600 text-left">
              <tr>
                <th className="px-4 py-3">Book</th>
                <th className="px-4 py-3">Issued</th>
                <th className="px-4 py-3">Due</th>
                <th className="px-4 py-3">Returned</th>
                <th className="px-4 py-3">Fine</th>
                <th className="px-4 py-3">Status</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {loans.map((loan) => (
                <tr key={loan.id}>
                  <td className="px-4 py-3 font-medium text-gray-800">{loan.bookTitle}</td>
                  <td className="px-4 py-3 text-gray-600">{loan.issueDate}</td>
                  <td className="px-4 py-3 text-gray-600">{loan.dueDate}</td>
                  <td className="px-4 py-3 text-gray-600">{loan.returnDate || "—"}</td>
                  <td className="px-4 py-3 text-gray-600">
                    {loan.fineAmount > 0 ? `₹${loan.fineAmount}` : "—"}
                  </td>
                  <td className="px-4 py-3">{statusBadge(loan)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <Pagination page={page} totalPages={totalPages} onChange={setPage} />
    </div>
  );
}
