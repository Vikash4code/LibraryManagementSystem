import { Link, useParams } from "react-router-dom";
import { getArticle, articles } from "../data/articles";

export default function BlogPost() {
  const { id } = useParams();
  const article = getArticle(id);

  if (!article) {
    return (
      <div className="max-w-3xl mx-auto px-4 py-20 text-center">
        <h1 className="text-2xl font-bold text-gray-800">Article not found</h1>
        <Link to="/blog" className="text-indigo-600 hover:underline mt-4 inline-block">
          ← Back to the blog
        </Link>
      </div>
    );
  }

  const related = articles.filter((a) => a.id !== article.id).slice(0, 3);

  return (
    <article className="max-w-3xl mx-auto px-4 py-10">
      <Link to="/blog" className="text-indigo-600 hover:underline text-sm">← Back to the blog</Link>

      <div className={`mt-4 h-48 rounded-2xl bg-gradient-to-br ${article.gradient} flex items-center justify-center text-6xl`}>
        {article.icon}
      </div>

      <div className="mt-6">
        <span className="text-xs text-indigo-600 font-medium">{article.tag}</span>
        <h1 className="mt-1 text-3xl font-bold text-gray-800">{article.title}</h1>
        <p className="text-sm text-gray-400 mt-2">
          By {article.author} · {article.date} · {article.readingTime}
        </p>
      </div>

      <div className="mt-6 space-y-4 text-gray-700 leading-relaxed">
        {article.content.map((paragraph, i) => (
          <p key={i}>{paragraph}</p>
        ))}
      </div>

      <div className="mt-12 border-t border-gray-200 pt-8">
        <h2 className="font-semibold text-gray-800 mb-4">More from the blog</h2>
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
          {related.map((a) => (
            <Link
              key={a.id}
              to={`/blog/${a.id}`}
              className="rounded-lg shadow-sm hover:shadow transition-shadow overflow-hidden bg-white"
            >
              <div className={`h-20 bg-gradient-to-br ${a.gradient} flex items-center justify-center text-2xl`}>
                {a.icon}
              </div>
              <p className="p-3 text-sm font-medium text-gray-700 line-clamp-2">{a.title}</p>
            </Link>
          ))}
        </div>
      </div>
    </article>
  );
}
