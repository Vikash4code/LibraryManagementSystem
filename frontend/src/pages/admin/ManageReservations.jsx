import { useEffect, useState } from "react";
import client from "../../api/client";
import Spinner from "../../components/Spinner";

const badgeStyles = {
  PENDING: "bg-amber-50 text-amber-700",
  FULFILLED: "bg-green-50 text-green-700",
  CANCELLED: "bg-gray-100 text-gray-500",
};

export default function ManageReservations() {
  const [reservations, setReservations] = useState(null);

  const load = () =>
    client.get("/reservations").then(({ data }) => setReservations(data));

  useEffect(() => {
    load();
  }, []);

  const handleCancel = async (id) => {
    if (!window.confirm("Cancel this reservation?")) return;
    await client.put(`/reservations/${id}/cancel`);
    load();
  };

  if (!reservations) return <Spinner />;

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800">Reservations</h1>
      <p className="text-sm text-gray-500 mt-1">
        Pending reservations are fulfilled automatically when the book is issued to that member.
      </p>

      {reservations.length === 0 ? (
        <p className="text-center text-gray-500 py-16">No reservations yet.</p>
      ) : (
        <div className="mt-6 bg-white rounded-lg shadow overflow-x-auto">
          <table className="w-full text-sm">
            <thead className="bg-gray-50 text-gray-600 text-left">
              <tr>
                <th className="px-4 py-3">Member</th>
                <th className="px-4 py-3">Book</th>
                <th className="px-4 py-3">Reserved</th>
                <th className="px-4 py-3">Status</th>
                <th className="px-4 py-3"></th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {reservations.map((r) => (
                <tr key={r.id}>
                  <td className="px-4 py-3 text-gray-800">{r.userName}</td>
                  <td className="px-4 py-3 font-medium text-gray-800">{r.bookTitle}</td>
                  <td className="px-4 py-3 text-gray-600">{r.reservedDate}</td>
                  <td className="px-4 py-3">
                    <span className={`text-xs px-2 py-1 rounded-full ${badgeStyles[r.status]}`}>
                      {r.status}
                    </span>
                  </td>
                  <td className="px-4 py-3 text-right">
                    {r.status === "PENDING" && (
                      <button
                        onClick={() => handleCancel(r.id)}
                        className="text-red-600 hover:underline"
                      >
                        Cancel
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
