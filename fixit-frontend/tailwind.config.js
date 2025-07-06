/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          light: '#534BAE',
          DEFAULT: '#1A237E',
          dark: '#000051',
        },
        secondary: {
          light: '#FFE082',
          DEFAULT: '#D4AF37',
          dark: '#A67C00',
        },
        background: {
          DEFAULT: '#FAFAFA',
        },
        text: {
          primary: '#0A192F',
          secondary: '#424242',
        },
      },
      fontFamily: {
        sans: ['Roboto', 'sans-serif'],
        serif: ['"Playfair Display"', 'serif'],
      },
    },
  },
  plugins: [],
}

