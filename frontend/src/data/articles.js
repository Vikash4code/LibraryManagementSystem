// Simple in-app blog content. For a small project this lives in the frontend
// instead of a database — easy to edit and no extra API needed.

export const articles = [
  {
    id: "build-a-reading-habit",
    title: "How to Build a Reading Habit That Actually Sticks",
    excerpt:
      "Reading more isn't about willpower — it's about designing your day so a book is the easiest thing to reach for.",
    author: "Editorial Team",
    date: "2026-05-28",
    readingTime: "5 min read",
    tag: "Reading",
    gradient: "from-indigo-500 to-purple-600",
    icon: "📖",
    content: [
      "Most people who want to read more start by setting a big goal — fifty books a year, a chapter a night. Goals are fine, but habits are what carry you when motivation fades. The trick is to make the habit small enough that you never have an excuse to skip it.",
      "Start with ten pages a day. Ten pages takes about fifteen minutes and feels almost too easy, which is exactly the point. A habit you never break beats an ambitious plan you abandon in February.",
      "Attach reading to something you already do. Read while your coffee brews, on your commute, or in the ten minutes before bed instead of scrolling. When a new habit borrows the trigger of an old one, it sticks far faster.",
      "Finally, keep the book visible. A book on your pillow or beside your keyboard quietly invites you in. Out of sight really is out of mind — so put your next chapter right in your path.",
    ],
  },
  {
    id: "timeless-classics",
    title: "Five Timeless Classics Every Reader Should Meet Once",
    excerpt:
      "A short, friendly starting list for anyone who wants to explore the books that shaped modern storytelling.",
    author: "Editorial Team",
    date: "2026-05-15",
    readingTime: "6 min read",
    tag: "Recommendations",
    gradient: "from-emerald-500 to-teal-600",
    icon: "🏛️",
    content: [
      "Classics can feel intimidating, but most earned their place by being genuinely enjoyable. Here are five that reward the time you give them without asking for a literature degree in return.",
      "To Kill a Mockingbird by Harper Lee is a warm, clear-eyed story about fairness told through a child's eyes. 1984 by George Orwell still feels startlingly current every time you open it. Pride and Prejudice by Jane Austen is sharper and funnier than its reputation suggests.",
      "The Great Gatsby by F. Scott Fitzgerald is short, beautiful, and quietly devastating — perfect for a single weekend. And The Alchemist by Paulo Coelho is a gentle fable about following what matters to you.",
      "You'll find every one of these in our catalogue. Pick whichever cover pulls you in — there's no wrong place to start.",
    ],
  },
  {
    id: "power-of-libraries",
    title: "The Quiet Power of Public Libraries",
    excerpt:
      "Libraries are one of the few places left that ask nothing of you but your curiosity. Here's why that still matters.",
    author: "Editorial Team",
    date: "2026-04-30",
    readingTime: "4 min read",
    tag: "Community",
    gradient: "from-amber-500 to-orange-600",
    icon: "🏫",
    content: [
      "A library is a rare kind of space: you can walk in, stay all day, and leave with a stack of books without spending a rupee. In a world that meters almost everything, that openness is quietly radical.",
      "Beyond books, libraries lend calm. They are reliable, quiet, and welcoming — a place to study, to work, or simply to think. For many students they are the first place that treats reading as something everyone deserves.",
      "Modern libraries also bridge the digital divide, offering free access to e-books and online catalogues. That's the spirit behind the E-Library in this app: thousands of public-domain titles you can read instantly, for free.",
    ],
  },
  {
    id: "digital-vs-physical",
    title: "Digital vs Physical Books: Finding Your Balance",
    excerpt:
      "You don't have to choose a side. The best readers use both — and know when each one shines.",
    author: "Editorial Team",
    date: "2026-04-12",
    readingTime: "5 min read",
    tag: "Reading",
    gradient: "from-sky-500 to-blue-600",
    icon: "📱",
    content: [
      "The paper-versus-screen debate never really ends, mostly because both sides are right. Physical books are easy on the eyes, simple to annotate, and pleasant to own. E-books are searchable, weightless, and always in your pocket.",
      "A practical rule: read deeply with paper, read widely with digital. Save print for the books you want to sit with and remember. Use e-books for travel, for trying new authors, and for instant access when a title catches your eye.",
      "This library supports both worlds — borrow physical copies from the catalogue, and read free digital titles straight from the E-Library. Use whichever fits the moment.",
    ],
  },
  {
    id: "how-this-was-built",
    title: "Behind the Scenes: How This Library System Was Built",
    excerpt:
      "A quick developer's tour of the architecture — Spring Boot, React, JWT auth, and a few decisions worth explaining.",
    author: "Developer",
    date: "2026-03-26",
    readingTime: "7 min read",
    tag: "Engineering",
    gradient: "from-fuchsia-500 to-pink-600",
    icon: "⚙️",
    content: [
      "This project is a full-stack Library Management System. The backend is a Spring Boot REST API written in Java 21, using Spring Data JPA over MySQL and Spring Security with JWT for stateless authentication. The frontend is a React single-page app styled with Tailwind CSS.",
      "A few design choices are worth calling out. DTOs are Java records because they are immutable and concise, while JPA entities use Lombok since they need mutable, no-argument constructors. Fines are calculated at the moment a book is returned, which keeps the rule in one place and avoids a background job.",
      "Security is stateless: a JSON Web Token carries the user's role, and a single filter validates it on every request. Role-based annotations protect admin-only actions like issuing books and managing the catalogue.",
      "The whole stack runs with a single docker compose up — MySQL, the API, and the web app start together, with nginx serving the React build and proxying API calls to the backend. It's a compact example of how the common pieces of a real web application fit together.",
    ],
  },
  {
    id: "organize-your-collection",
    title: "Organizing Your Personal Book Collection",
    excerpt:
      "A few light-touch systems to tame an overflowing shelf without turning your hobby into a chore.",
    author: "Editorial Team",
    date: "2026-03-08",
    readingTime: "4 min read",
    tag: "Tips",
    gradient: "from-rose-500 to-red-600",
    icon: "📚",
    content: [
      "Every reader eventually faces the happy problem of too many books. The goal of organizing isn't a magazine-perfect shelf — it's being able to find what you want and enjoy what you own.",
      "Pick one simple system and stick with it. By genre is the most intuitive for most people; by author suits collectors; by colour is purely for the joy of it. There is no correct answer, only the one you'll actually maintain.",
      "Keep a small 'to-read' shelf separate from the rest so your next book is always obvious. And don't be afraid to let books go — a collection you love is better than one you merely store.",
    ],
  },
];

export function getArticle(id) {
  return articles.find((a) => a.id === id);
}
