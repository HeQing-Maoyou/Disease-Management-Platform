module.exports = {
  root: true,
  env: {
    node: true,
    browser: true,
  },
  extends: [
    'eslint:recommended',
    'plugin:vue/vue3-recommended',
    'plugin:prettier/recommended'
  ],
  parserOptions: {
    ecmaVersion: 2020,
    sourceType: 'module'
  },
  globals: {
    AMap: 'readonly'
  },
  rules: {
    'no-unused-vars': ['warn'],
    'vue/html-self-closing': 'off',
    'vue/multi-word-component-names': 'off'
  }
}

