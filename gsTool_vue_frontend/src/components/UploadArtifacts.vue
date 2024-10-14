<template>
  <div>
    <h1>上传圣遗物数据</h1>
    <input type="file" @change="handleFileUpload" accept=".json" />
    <input type="text" v-model="userId" placeholder="请输入用户ID" />  <!-- 用户输入 user_ID -->
    <button @click="submitFile">上传</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      selectedFile: null,  // 用于存储选择的文件
      userId: '',
    };
  },
  methods: {
    handleFileUpload(event) {
      this.selectedFile = event.target.files[0];  // 获取文件对象
    },
    async submitFile() {
      if (!this.selectedFile || !this.userId) {
        alert("请选择文件并输入用户ID");
        return;
      }

      const formData = new FormData();
      formData.append("file", this.selectedFile);
      formData.append("userId", this.userId);

      try {
        // 发送文件到后端
        const response = await axios.post("http://localhost:8084/api/upload-artifacts", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        console.log(response);
        alert("圣遗物数据上传成功！");
      } catch (error) {
        console.error("上传失败", error);
        alert("上传失败，请重试！");
      }
    },
  },
};
</script>

<style scoped>
h1 {
  margin-bottom: 20px;
}
</style>