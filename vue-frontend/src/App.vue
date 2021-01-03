<template>
  <div id="app">
    <ForteHeader> </ForteHeader>
    <img alt="Vue logo" src="./assets/logo.png" />
    <MainTaskView tasks="tasks" />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import MainTaskView from "@/components/MainTaskView.vue";
import TaskViewElement from "@/components/TaskViewElement.vue";
import ForteHeader from "@/components/ForteHeader.vue";
import axios from "axios";
import Task from "@/logic/Task";

@Component({
  components: {
    ForteHeader,
    MainTaskView,
    TaskViewElement
  },
  data() {
    return {
      tasks: {
        type: [] as Task[]
      }
    };
  },
  created() {
    axios({
      baseURL: "http://localhost:8000/tasks",
      method: "GET"
    }).then(response => {
      this.$data.tasks = response.data;
    });
  },
  methods: {}
})
export default class App extends Vue {}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
