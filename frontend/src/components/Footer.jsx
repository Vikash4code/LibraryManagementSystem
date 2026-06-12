import { Link } from "react-router-dom";

export default function Footer() {
  return (
    <footer className="bg-gray-900 text-gray-300 mt-16">
      <div className="max-w-7xl mx-auto px-4 py-10 grid grid-cols-1 sm:grid-cols-3 gap-8">
        <div>
          <h3 className="text-white font-bold text-lg">📚 City Library</h3>
          <p className="text-sm text-gray-400 mt-2">
            A modern library management system — browse, borrow, reserve and read
            books online, all in one place.
          </p>
        </div>

        <div>
          <h4 className="text-white font-semibold mb-3">Explore</h4>
          <ul className="space-y-2 text-sm">
            <li><Link to="/" className="hover:text-white">Home</Link></li>
            <li><Link to="/books" className="hover:text-white">Book Catalogue</Link></li>
            <li><Link to="/elibrary" className="hover:text-white">E-Library</Link></li>
            <li><Link to="/blog" className="hover:text-white">Blog</Link></li>
            <li><Link to="/contact" className="hover:text-white">Contact</Link></li>
          </ul>
        </div>

        <div>
          <h4 className="text-white font-semibold mb-3">Get started</h4>
          <ul className="space-y-2 text-sm">
            <li><Link to="/register" className="hover:text-white">Create an account</Link></li>
            <li><Link to="/login" className="hover:text-white">Sign in</Link></li>
          </ul>
        </div>
      </div>

      <div className="border-t border-gray-800">
        <p className="max-w-7xl mx-auto px-4 py-4 text-xs text-gray-500 text-center">
          © {new Date().getFullYear()} City Library. Built as a full-stack developer project.
        </p>
      </div>
    </footer>
  );
}
