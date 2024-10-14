import { createRouter, createWebHistory } from 'vue-router';
import AddData from '../components/AddData.vue';
import UploadArtifacts from "@/components/UploadArtifacts.vue";

const routes = [
    { path: '/add-data', component: AddData },
    { path: '/upload-artifacts', component: UploadArtifacts }
];


const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;