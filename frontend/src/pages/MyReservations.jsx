import { useEffect, useState } from "react";
import client from "../api/client";
import Spinner from "../components/Spinner";

const badgeStyles = {
  PENDING: "bg-amber-50 text-amber-700",
  FULFILLED: "bg-green-50 text-green-700",
  CANCELLED: "bg-gray-100 text-gray-500",
};

export default function MyReservations() {
  const [reservations, setReservations] = useState(null);

  const load = () =>
    client.get("/reservations/my").then(({ data }) => setReservations(data));

  useEffect(() => {
    load();
  }, []);

  const handleCancel = async (id) => {
    await client.put(`/reservations/${id}/cancel`);
    load();
  };

  if (!reservations) return <Spinner />;

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800">My Reservations</h1>

      {reservations.length === 0 ? (
        <p className="text-center text-gray-500 py-16">
          No reservations. You can reserve a book when all copies are issued.
        </p>
      ) : (
        <div className="mt-6 space-y-3">
          {reservations.map((r) => (
            <div
              key={r.id}
              className="bg-white rounded-lg shadow p-4 flex items-center justify-between"
            >
              <div>
                <p className="font-medium text-gray-800">{r.bookTitle}</p>
                <p className="text-sm text-gray-500">Reserved on {r.reservedDate}</p>
              </div>
              <div className="flex items-center gap-3">
                <span className={`text-xs px-2 py-1 rounded-full ${badgeStyles[r.status]}`}>
                  {r.status}
                </span>
                {r.status === "PENDING" && (
                  <button
                    onClick={() => handleCancel(r.id)}
                    className="text-sm text-red-600 hover:underline"
                  >
                    Cancel
                  </button>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
