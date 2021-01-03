<template>
  <div class="task-view-element">
    TVE
    {{ task.name }}
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import Task from "@/logic/Task";

@Component
export default class TaskViewElement extends Vue {
  @Prop() private task!: Task;
}
</script>

<style scoped>
.task-view-element {
  border-color: black;
  border-width: thin;
}
</style>
