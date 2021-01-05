<template>
  <div id="app">
    <ForteHeader> </ForteHeader>
    <img alt="Vue logo" src="./assets/logo.png" />
    <MainTaskView tasks="tasks" />
  </div>
</template>

<script>
import ForteHeader from "@/components/ForteHeader";
import MainTaskView from "@/components/MainTaskView";
import axios from "axios";

export default {
  name: 'App',
  components: {
    ForteHeader,
    MainTaskView
  },
  data: function() {
    return {
      tasks: []
    };
  },
  mounted() {
    axios({
      baseURL: "http://localhost:8000/tasks",
      method: "GET"
    }).then(response => {
      this.data.tasks = response.data;
    });
  },
}
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
