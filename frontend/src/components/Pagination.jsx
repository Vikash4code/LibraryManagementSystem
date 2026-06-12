export default function Pagination({ page, totalPages, onChange }) {
  if (totalPages <= 1) return null;

  return (
    <div className="flex items-center justify-center gap-2 mt-6">
      <button
        disabled={page === 0}
        onClick={() => onChange(page - 1)}
        className="px-3 py-1.5 text-sm rounded-md border border-gray-300 bg-white disabled:opacity-40 hover:bg-gray-50"
      >
        Previous
      </button>
      <span className="text-sm text-gray-600">
        Page {page + 1} of {totalPages}
      </span>
      <button
        disabled={page >= totalPages - 1}
        onClick={() => onChange(page + 1)}
        className="px-3 py-1.5 text-sm rounded-md border border-gray-300 bg-white disabled:opacity-40 hover:bg-gray-50"
      >
        Next
      </button>
    </div>
  );
}
