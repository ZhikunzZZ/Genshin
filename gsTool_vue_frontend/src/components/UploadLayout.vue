<template>
  <div class="container">
    <div class="header"></div>
    <div class="box1"></div>
    <div class="box2"></div>
    <div class="box3"></div>
    <div class="box4"></div>
    <div class="small-box1"></div>
    <div class="small-box2"></div>
    <div class="small-box3"></div>
    <div class="small-box4"></div>
  </div>
</template>

<script>
export default {
  name: "UploadLayout",
};
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f8a7a7;
}

.container {
  display: grid;
  grid-template-columns: 1fr 1fr 2fr;
  grid-template-rows: 0.2fr 1fr 0.7fr;
  gap: 10px;
  width: 80%;
  height: 90%;
  padding: 20px;
}

.header {
  grid-column: 1 / 2;
  grid-row: 1 / 2;
  background-color: #ccc;
}

.box1 {
  grid-column: 1 / 2;
  grid-row: 2 / 3;
  background-color: #d3d3d3;
}

.box2 {
  grid-column: 2 / 3;
  grid-row: 2 / 3;
  background-color: #d3d3d3;
}

.box3 {
  grid-column: 3 / 4;
  grid-row: 1 / 3;
  background-color: #d3d3d3;
}

.box4 {
  grid-column: 1 / 3;
  grid-row: 3 / 4;
  background-color: #d3d3d3;
  border: 2px solid blue;
}

.small-box1, .small-box2, .small-box3, .small-box4 {
  background-color: #d3d3d3;
}

.small-box1 {
  grid-column: 3 / 4;
  grid-row: 3 / 4;
}

.small-box2 {
  grid-column: 3 / 4;
  grid-row: 4 / 5;
}

.small-box3 {
  grid-column: 3 / 4;
  grid-row: 5 / 6;
}

.small-box4 {
  grid-column: 3 / 4;
  grid-row: 6 / 7;
}
</style>