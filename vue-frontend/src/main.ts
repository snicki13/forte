import Vue from "vue";
import App from "./App.vue";
import Task from "@/logic/Task";

Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
}).$mount("#app");
