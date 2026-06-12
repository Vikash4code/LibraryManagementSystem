import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const linkClass = ({ isActive }) =>
  `px-3 py-2 rounded-md text-sm font-medium ${
    isActive ? "bg-indigo-700 text-white" : "text-indigo-100 hover:bg-indigo-500"
  }`;

export default function Navbar() {
  const { user, isAdmin, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="bg-indigo-600 shadow">
      <div className="max-w-7xl mx-auto px-4 flex items-center justify-between h-14">
        <div className="flex items-center gap-1">
          <Link to="/" className="text-white font-bold text-lg mr-4">
            📚 City Library
          </Link>
          <NavLink to="/" end className={linkClass}>Home</NavLink>
          <NavLink to="/books" className={linkClass}>Books</NavLink>
          <NavLink to="/elibrary" className={linkClass}>E-Library</NavLink>
          <NavLink to="/blog" className={linkClass}>Blog</NavLink>
          <NavLink to="/contact" className={linkClass}>Contact</NavLink>
          {user && <NavLink to="/dashboard" className={linkClass}>Dashboard</NavLink>}
          {user && !isAdmin && (
            <>
              <NavLink to="/my-loans" className={linkClass}>My Loans</NavLink>
              <NavLink to="/my-reservations" className={linkClass}>Reservations</NavLink>
              <NavLink to="/wishlist" className={linkClass}>Wishlist</NavLink>
            </>
          )}
          {isAdmin && (
            <>
              <NavLink to="/admin/books" className={linkClass}>Manage Books</NavLink>
              <NavLink to="/admin/loans" className={linkClass}>Loans</NavLink>
              <NavLink to="/admin/reservations" className={linkClass}>Reservations</NavLink>
            </>
          )}
        </div>
        <div className="flex items-center gap-3">
          {user ? (
            <>
              <span className="text-indigo-100 text-sm hidden sm:block">
                Hi, {user.name.split(" ")[0]}
              </span>
              <button
                onClick={handleLogout}
                className="bg-indigo-800 text-white text-sm px-3 py-1.5 rounded-md hover:bg-indigo-900"
              >
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="text-indigo-100 text-sm hover:text-white">Login</Link>
              <Link
                to="/register"
                className="bg-white text-indigo-600 text-sm px-3 py-1.5 rounded-md font-medium hover:bg-indigo-50"
              >
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}
