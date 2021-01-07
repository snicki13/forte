<template>
  <div id="app">
    <ForteHeader> </ForteHeader>
    <img alt="Vue logo" src="./assets/logo.png" />
    <MainTaskView v-bind:tasks=tasks />
  </div>
</template>

<script>
import ForteHeader from "@/components/ForteHeader";
import MainTaskView from "@/components/MainTaskView";

export default {
  name: 'App',
  components: {
    ForteHeader,
    MainTaskView
  }
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
