<template>
  <div class="task-view">
    <TaskViewElement
      v-for="task in tasks"
      v-bind:key="task.id"
      v-bind:task="task"
    ></TaskViewElement>
  </div>
</template>

<script>

import TaskViewElement from "@/components/TaskViewElement";
import axios from "axios";

export default {
  name: 'MainTaskView',
  components: {
    TaskViewElement
  },
  data() {
    return {
      tasks: []
    }
  },
  async created() {
    try {
      const res = await axios.get("http://localhost:8000/tasks");
      this.tasks = res.data;
    } catch (e) {
      console.log(e)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.taskView {
  alignment: center;
  margin: 10px;
}
</style>

