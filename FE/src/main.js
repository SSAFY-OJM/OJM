import { createApp } from "vue";
import { createPinia } from "pinia";
import "./assets/common.css";
import "./global.css";
import App from "./App.vue";
import router from "./router";
import "./index.css";
import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { store } from "./store"; // 스토어 import
const app = createApp(App);
app.component("VueDatePicker", VueDatePicker);
app.use(store);
app.use(createPinia());
app.use(router);

app.mount("#app");
