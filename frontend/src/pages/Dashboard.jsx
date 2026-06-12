import { useEffect, useState } from "react";
import {
  BarChart, Bar, PieChart, Pie, Cell, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend,
} from "recharts";
import client from "../api/client";
import StatCard from "../components/StatCard";
import Spinner from "../components/Spinner";
import { useAuth } from "../context/AuthContext";

const PIE_COLORS = ["#6366f1", "#22c55e", "#f59e0b", "#ef4444", "#06b6d4", "#a855f7", "#84cc16"];

export default function Dashboard() {
  const { user, isAdmin } = useAuth();
  const [stats, setStats] = useState(null);

  useEffect(() => {
    client.get("/dashboard").then(({ data }) => setStats(data));
  }, []);

  if (!stats) return <Spinner />;

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-800">
        {isAdmin ? "Library Overview" : `Welcome, ${user.name.split(" ")[0]}`}
      </h1>

      {isAdmin ? (
        <>
          <div className="mt-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <StatCard label="Total Books" value={stats.totalBooks} icon="📚" />
            <StatCard label="Members" value={stats.totalUsers} icon="👥" color="green" />
            <StatCard label="Active Loans" value={stats.activeLoans} icon="📤" color="amber" />
            <StatCard label="Overdue" value={stats.overdueLoans} icon="⚠️" color="red" />
          </div>

          <div className="mt-8 grid grid-cols-1 lg:grid-cols-2 gap-6">
            <div className="bg-white rounded-lg shadow p-5">
              <h2 className="font-semibold text-gray-700 mb-4">Books per category</h2>
              <ResponsiveContainer width="100%" height={280}>
                <PieChart>
                  <Pie
                    data={stats.booksPerCategory}
                    dataKey="count"
                    nameKey="category"
                    outerRadius={100}
                    label={(entry) => entry.category}
                  >
                    {stats.booksPerCategory.map((entry, index) => (
                      <Cell key={entry.category} fill={PIE_COLORS[index % PIE_COLORS.length]} />
                    ))}
                  </Pie>
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </div>

            <div className="bg-white rounded-lg shadow p-5">
              <h2 className="font-semibold text-gray-700 mb-4">Loans per month</h2>
              <ResponsiveContainer width="100%" height={280}>
                <BarChart data={stats.loansPerMonth}>
                  <XAxis dataKey="month" />
                  <YAxis allowDecimals={false} />
                  <Tooltip />
                  <Legend />
                  <Bar dataKey="count" name="Loans" fill="#6366f1" radius={[4, 4, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>
        </>
      ) : (
        <div className="mt-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <StatCard label="Active Loans" value={stats.activeLoans} icon="📖" />
          <StatCard label="Overdue" value={stats.overdueLoans} icon="⚠️" color="red" />
          <StatCard label="Total Borrowed" value={stats.totalBorrowed} icon="📚" color="green" />
          <StatCard label="Total Fines" value={`₹${stats.totalFines}`} icon="💰" color="amber" />
        </div>
      )}
    </div>
  );
}
