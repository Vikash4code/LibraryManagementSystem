import { useState } from "react";

// Update these with your own details.
const DEVELOPER = {
  email: "you@example.com",
  github: "https://github.com/Vikash4code",
  linkedin: "https://www.linkedin.com/",
};

export default function Contact() {
  const [form, setForm] = useState({ name: "", email: "", message: "" });
  const [sent, setSent] = useState(false);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    // No backend mailbox in this project — open the user's email client instead.
    const subject = encodeURIComponent(`Library message from ${form.name}`);
    const body = encodeURIComponent(`${form.message}\n\nReply to: ${form.email}`);
    window.location.href = `mailto:${DEVELOPER.email}?subject=${subject}&body=${body}`;
    setSent(true);
  };

  return (
    <div className="max-w-5xl mx-auto px-4 py-12">
      <div className="text-center">
        <h1 className="text-3xl font-bold text-gray-800">Get in touch</h1>
        <p className="text-gray-500 mt-2">
          Questions, feedback or just want to say hi? Send a message — I'd be glad to hear from you.
        </p>
      </div>

      <div className="mt-10 grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Contact details */}
        <div className="space-y-4">
          <a
            href={`mailto:${DEVELOPER.email}`}
            className="block bg-white rounded-xl shadow p-5 hover:shadow-md"
          >
            <div className="text-2xl">✉️</div>
            <h3 className="mt-2 font-semibold text-gray-800">Email</h3>
            <p className="text-sm text-gray-500 break-all">{DEVELOPER.email}</p>
          </a>
          <a
            href={DEVELOPER.github}
            target="_blank"
            rel="noreferrer"
            className="block bg-white rounded-xl shadow p-5 hover:shadow-md"
          >
            <div className="text-2xl">💻</div>
            <h3 className="mt-2 font-semibold text-gray-800">GitHub</h3>
            <p className="text-sm text-gray-500">See the source code & more projects</p>
          </a>
          <a
            href={DEVELOPER.linkedin}
            target="_blank"
            rel="noreferrer"
            className="block bg-white rounded-xl shadow p-5 hover:shadow-md"
          >
            <div className="text-2xl">🔗</div>
            <h3 className="mt-2 font-semibold text-gray-800">LinkedIn</h3>
            <p className="text-sm text-gray-500">Let's connect</p>
          </a>
        </div>

        {/* Contact form */}
        <div className="lg:col-span-2 bg-white rounded-xl shadow p-6">
          {sent && (
            <div className="mb-4 bg-green-50 text-green-700 text-sm rounded-md px-4 py-3">
              Thanks for reaching out! Your email app should have opened with your message ready to send.
            </div>
          )}
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700">Your name</label>
                <input
                  name="name"
                  required
                  value={form.name}
                  onChange={handleChange}
                  className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  placeholder="Jane Doe"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Your email</label>
                <input
                  name="email"
                  type="email"
                  required
                  value={form.email}
                  onChange={handleChange}
                  className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  placeholder="jane@example.com"
                />
              </div>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Message</label>
              <textarea
                name="message"
                required
                rows={6}
                value={form.message}
                onChange={handleChange}
                className="mt-1 w-full rounded-md border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                placeholder="Write your message here..."
              />
            </div>
            <button
              type="submit"
              className="bg-indigo-600 text-white px-6 py-2.5 rounded-md font-medium hover:bg-indigo-700"
            >
              Send message
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
