import './assets/main.scss'

import { createPinia } from 'pinia'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'

import router from './router'
import App from '@/App.vue'
import { createApp } from 'vue'

const app = createApp(App)
app.use(Buefy)
app.use(router)
app.use(createPinia())
app.mount('#app')
