import { Link } from "react-router-dom";
import { articles } from "../data/articles";

export default function Blog() {
  return (
    <div className="max-w-7xl mx-auto px-4 py-10">
      <div className="text-center">
        <h1 className="text-3xl font-bold text-gray-800">The Library Blog</h1>
        <p className="text-gray-500 mt-2">
          Reading tips, recommendations and notes on books — and the occasional look behind the scenes.
        </p>
      </div>

      <div className="mt-10 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {articles.map((a) => (
          <Link
            key={a.id}
            to={`/blog/${a.id}`}
            className="rounded-xl shadow hover:shadow-md transition-shadow overflow-hidden bg-white flex flex-col"
          >
            <div className={`h-40 bg-gradient-to-br ${a.gradient} flex items-center justify-center text-5xl`}>
              {a.icon}
            </div>
            <div className="p-5 flex-1 flex flex-col">
              <span className="text-xs text-indigo-600 font-medium">{a.tag}</span>
              <h2 className="mt-1 font-semibold text-gray-800 line-clamp-2">{a.title}</h2>
              <p className="mt-2 text-sm text-gray-500 line-clamp-3">{a.excerpt}</p>
              <div className="mt-auto pt-4 flex items-center justify-between text-xs text-gray-400">
                <span>{a.date}</span>
                <span>{a.readingTime}</span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}
