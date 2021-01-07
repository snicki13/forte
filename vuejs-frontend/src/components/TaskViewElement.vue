<template>
  <div class="task-view-element" v-bind:style="{ backgroundColor: task.color }">
    TVE
    {{ task.name }}
  </div>
</template>

<script>

import Task from "../logic/Task";

export default {
  name: 'TaskViewElement',
  props: {
    task: Task
  }
}
</script>

<style>
.task-view-element {
  float: left;
  border-color: black;
  border-width: thin;
  border-radius: 20px;
  height: 90px;
  width: 130px;
  padding: 10px;
  margin: 10px;
}
</style>
