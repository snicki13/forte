<template>
  <div class="task-view-element">
    TVE
    {{ task.name }}
  </div>
</template>

<script lang="ts">
import Task from "@/logic/Task";

export default {
  name: 'TaskViewElement',
  props: {
    task: Task
  }
}
</script>

<style scoped>
.task-view-element {
  border-color: black;
  border-width: thin;
}
</style>
