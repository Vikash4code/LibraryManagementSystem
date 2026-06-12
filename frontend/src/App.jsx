import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { ProtectedRoute, AdminRoute } from "./components/ProtectedRoute";
import Home from "./pages/Home";
import Blog from "./pages/Blog";
import BlogPost from "./pages/BlogPost";
import Contact from "./pages/Contact";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Books from "./pages/Books";
import BookDetail from "./pages/BookDetail";
import Dashboard from "./pages/Dashboard";
import MyLoans from "./pages/MyLoans";
import MyReservations from "./pages/MyReservations";
import Wishlist from "./pages/Wishlist";
import ELibrary from "./pages/ELibrary";
import ManageBooks from "./pages/admin/ManageBooks";
import ManageLoans from "./pages/admin/ManageLoans";
import ManageReservations from "./pages/admin/ManageReservations";

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="min-h-screen bg-gray-100 flex flex-col">
          <Navbar />
          <main className="flex-1">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/blog" element={<Blog />} />
            <Route path="/blog/:id" element={<BlogPost />} />
            <Route path="/contact" element={<Contact />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/books" element={<Books />} />
            <Route path="/books/:id" element={<BookDetail />} />
            <Route path="/elibrary" element={<ELibrary />} />

            <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
            <Route path="/my-loans" element={<ProtectedRoute><MyLoans /></ProtectedRoute>} />
            <Route path="/my-reservations" element={<ProtectedRoute><MyReservations /></ProtectedRoute>} />
            <Route path="/wishlist" element={<ProtectedRoute><Wishlist /></ProtectedRoute>} />

            <Route path="/admin/books" element={<AdminRoute><ManageBooks /></AdminRoute>} />
            <Route path="/admin/loans" element={<AdminRoute><ManageLoans /></AdminRoute>} />
            <Route path="/admin/reservations" element={<AdminRoute><ManageReservations /></AdminRoute>} />

            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
          </main>
          <Footer />
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}
