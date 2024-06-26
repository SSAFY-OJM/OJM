// /** @type {import('tailwindcss').Config} */
// export default {
//   content: [],
//   theme: {
//     extend: {},
//   },
//   plugins: [],
// }

export default {
  purge: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      underlineOffset: {
        5: "5px",
      },
    },
  },
  variants: {
    extend: {},
  },
  plugins: ["tailwind-scrollbar-hide"],
};
