<template>
  <div>
    <h2>添加数据</h2>
    <form @submit.prevent="submitForm">
      <div>
        <label for="id">ID:</label>
        <input type="text" id="id" v-model="id" placeholder="输入ID" required />
      </div>
      <div>
        <label for="name">Name:</label>
        <input type="text" id="name" v-model="name" placeholder="输入Name" required />
      </div>
      <button type="submit">确定</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      id: '',
      name: '',
      message: ''
    };
  },
  methods: {
    submitForm() {
      const requestData = {
        id: this.id,
        name: this.name
      };

      axios.post('http://localhost:8084/test/add', requestData)
          .then(response => {
            console.log(response); // 输出 response，避免 ESLint 报错
            this.message = '数据添加成功！';
          })
          .catch(error => {
            this.message = '添加数据时出错：' + error.message;
          });
    }
  }
};
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  max-width: 300px;
}

div {
  margin-bottom: 10px;
}

input {
  padding: 8px;
  font-size: 16px;
}

button {
  padding: 8px 12px;
  font-size: 16px;
  cursor: pointer;
}

p {
  color: green;
  margin-top: 10px;
}
</style>