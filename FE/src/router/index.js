import { createRouter, createWebHistory } from "vue-router";
import UserMainView from "@/views/UserMainView.vue";
import AdminMainView from "@/views/AdminMainView.vue";
import BoardWrite from "@/views/Board/BoardWrite.vue";
import BoardDetail from "@/views/Board/BoardDetail.vue";
import BoardList from "@/views/Board/BoardList.vue";
import FeedbackList from "@/views/Feedback/FeedbackList.vue";
import MyFeedbackList from "@/views/Feedback/MyFeedbackList.vue";

import ChatGPT from "@/views/Board/ChatGPT.vue";
import Join from "@/views/Member/Join.vue";
import Login from "@/views/Member/Login.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "usermain",
      component: UserMainView,
    },
    {
      path: "/admin",
      name: "adminmain",
      component: AdminMainView,
    },
    {
      path: "/admin/board/write",
      name: "BoardWrite",
      component: BoardWrite,
    },
    {
      path: "/admin/board/detail/:boardId",
      name: "BoardDetail",
      component: BoardDetail,
    },
    {
      path: "/admin/board/list",
      name: "BoardList",
      component: BoardList,
    },
    {
      path: "/admin/feedback/list",
      name: "FeedbackList",
      component: FeedbackList,
    },
    {
      path: "/gpt",
      name: "ChatGPT",
      component: ChatGPT,
    },
    {
      path: "/join",
      name: "Join",
      component: Join,
    },
    {
      path: "/login",
      name: "Login",
      component: Login,
    },
    {
      path: "/feedback/list/:userId",
      name: "MyFeedbackList",
      component: MyFeedbackList,
    },
  ],
});

export default router;
