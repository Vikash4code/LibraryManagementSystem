export default function StatCard({ label, value, icon, color = "indigo" }) {
  const colors = {
    indigo: "bg-indigo-50 text-indigo-700",
    green: "bg-green-50 text-green-700",
    red: "bg-red-50 text-red-700",
    amber: "bg-amber-50 text-amber-700",
  };

  return (
    <div className="bg-white rounded-lg shadow p-5 flex items-center gap-4">
      <div className={`h-12 w-12 rounded-full flex items-center justify-center text-xl ${colors[color]}`}>
        {icon}
      </div>
      <div>
        <p className="text-sm text-gray-500">{label}</p>
        <p className="text-2xl font-bold text-gray-800">{value}</p>
      </div>
    </div>
  );
}
