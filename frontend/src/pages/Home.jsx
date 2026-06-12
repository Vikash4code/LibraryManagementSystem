import { Link } from "react-router-dom";
import { articles } from "../data/articles";
import { useAuth } from "../context/AuthContext";

const features = [
  { icon: "🔍", title: "Browse & Search", text: "Explore the catalogue by title, author or category with instant search and filters." },
  { icon: "📤", title: "Borrow & Return", text: "Issue books with a clear due date and automatic fine tracking on late returns." },
  { icon: "🔖", title: "Reserve & Wishlist", text: "Reserve books that are out of stock and save your favourites for later." },
  { icon: "🌐", title: "Free E-Library", text: "Read and download thousands of free public-domain books, right in your browser." },
  { icon: "📊", title: "Dashboards", text: "Track your loans, fines and history — admins get library-wide insights and charts." },
  { icon: "🔔", title: "Reminders", text: "Get a friendly email reminder before your borrowed books are due." },
];

export default function Home() {
  const { user } = useAuth();
  const latest = articles.slice(0, 3);

  return (
    <div>
      {/* Hero */}
      <section className="bg-gradient-to-br from-indigo-600 via-indigo-700 to-purple-700 text-white">
        <div className="max-w-7xl mx-auto px-4 py-20 text-center">
          <h1 className="text-4xl sm:text-5xl font-extrabold leading-tight">
            Your whole library, <span className="text-indigo-200">in one place</span>
          </h1>
          <p className="mt-4 text-lg text-indigo-100 max-w-2xl mx-auto">
            Browse the catalogue, borrow and reserve books, read free titles online,
            and keep track of everything from a clean, modern dashboard.
          </p>
          <div className="mt-8 flex flex-wrap gap-4 justify-center">
            <Link
              to="/books"
              className="bg-white text-indigo-700 px-6 py-3 rounded-lg font-semibold hover:bg-indigo-50"
            >
              Browse Catalogue
            </Link>
            {!user && (
              <Link
                to="/register"
                className="border border-indigo-200 text-white px-6 py-3 rounded-lg font-semibold hover:bg-indigo-500"
              >
                Join the Library
              </Link>
            )}
          </div>
        </div>
      </section>

      {/* Features */}
      <section className="max-w-7xl mx-auto px-4 py-16">
        <h2 className="text-2xl font-bold text-gray-800 text-center">Everything you need to read more</h2>
        <p className="text-gray-500 text-center mt-2">A complete set of tools for members and librarians alike.</p>
        <div className="mt-10 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {features.map((f) => (
            <div key={f.title} className="bg-white rounded-xl shadow p-6">
              <div className="text-3xl">{f.icon}</div>
              <h3 className="mt-3 font-semibold text-gray-800">{f.title}</h3>
              <p className="mt-1 text-sm text-gray-500">{f.text}</p>
            </div>
          ))}
        </div>
      </section>

      {/* Latest articles */}
      <section className="bg-white">
        <div className="max-w-7xl mx-auto px-4 py-16">
          <div className="flex items-center justify-between">
            <h2 className="text-2xl font-bold text-gray-800">From the Blog</h2>
            <Link to="/blog" className="text-indigo-600 font-medium hover:underline text-sm">
              View all articles →
            </Link>
          </div>
          <div className="mt-8 grid grid-cols-1 sm:grid-cols-3 gap-6">
            {latest.map((a) => (
              <Link
                key={a.id}
                to={`/blog/${a.id}`}
                className="rounded-xl shadow hover:shadow-md transition-shadow overflow-hidden bg-white flex flex-col"
              >
                <div className={`h-32 bg-gradient-to-br ${a.gradient} flex items-center justify-center text-4xl`}>
                  {a.icon}
                </div>
                <div className="p-5 flex-1 flex flex-col">
                  <span className="text-xs text-indigo-600 font-medium">{a.tag}</span>
                  <h3 className="mt-1 font-semibold text-gray-800 line-clamp-2">{a.title}</h3>
                  <p className="mt-2 text-sm text-gray-500 line-clamp-2">{a.excerpt}</p>
                  <span className="mt-auto pt-3 text-xs text-gray-400">{a.date} · {a.readingTime}</span>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Contact CTA */}
      <section className="max-w-7xl mx-auto px-4 py-16">
        <div className="bg-indigo-600 rounded-2xl px-8 py-12 text-center text-white">
          <h2 className="text-2xl font-bold">Have a question or some feedback?</h2>
          <p className="mt-2 text-indigo-100">We'd love to hear from you — reach out any time.</p>
          <Link
            to="/contact"
            className="inline-block mt-6 bg-white text-indigo-700 px-6 py-3 rounded-lg font-semibold hover:bg-indigo-50"
          >
            Contact Us
          </Link>
        </div>
      </section>
    </div>
  );
}
