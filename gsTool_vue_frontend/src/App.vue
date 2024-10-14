<template>
  <div id="app">
    <nav>
      <router-link to="/add-data">添加数据</router-link>
    </nav>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
nav {
  margin-bottom: 20px;
}
</style>