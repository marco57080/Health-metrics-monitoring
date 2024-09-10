// https://nuxt.com/docs/api/configuration/nuxt-config


const ONE_DAY = 60 * 60 * 24 * 1000;
const ONE_WEEK = ONE_DAY * 7;


export default defineNuxtConfig({
  extends: ['@nuxt/ui-pro'],

  modules: [
    '@nuxt/eslint',
    '@nuxt/fonts',
    '@nuxt/ui',
    '@vueuse/nuxt',
    'nuxt-echarts'
  ],

  ui: {
    safelistColors: ['primary', 'red', 'orange', 'green']
  },

  colorMode: {
    disableTransition: true
  },

  routeRules: {
    // Temporary workaround for prerender regression. see https://github.com/nuxt/nuxt/issues/27490
    '/': { prerender: true }
  },

  devtools: {
    enabled: true
  },

  typescript: {
    strict: false
  },

  future: {
    compatibilityVersion: 4
  },

  eslint: {
    config: {
      stylistic: {
        commaDangle: 'never',
        braceStyle: '1tbs'
      }
    }
  },

  echarts: {
    ssr: true,
    renderer: ['canvas', 'svg'],
    charts: ['BarChart', 'LineChart'],
    components: ['DatasetComponent', 'GridComponent', 'TooltipComponent','ToolboxComponent'],
  },

  runtimeConfig: {
    cookieName: "__session",
    cookieSecret: "secret",
    cookieExpires: ONE_DAY.toString(),
    cookieRememberMeExpires: ONE_WEEK.toString(),
  },

  compatibilityDate: '2024-07-11'
})
