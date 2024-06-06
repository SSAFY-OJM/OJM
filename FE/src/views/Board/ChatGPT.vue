<template>
  <div class="flex flex-col mt-[60px]">
    <div class="chat-container h-[250px] flex flex-col justify-center items-center w-[350px]">
      <div class="chat-box overflow-y-auto scrollbar-hide">
        <div v-for="(message, index) in messages" :key="index" class="message">
          <strong>{{ message.sender }}:</strong> {{ message.text }}
        </div>
      </div>
      <div class="input-box">
        <input v-model="userMessage" @keyup.enter="debouncedSendMessage" placeholder="메시지를 입력하세요" />
        <button @click="debouncedSendMessage">전송</button>
      </div>
    </div>
    <div class="input-box2 flex justify-center mt-3">
      <button @click="onClickTodayMenu">오늘의 메뉴 추천 받기</button>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { debounce } from "lodash";

const userMessage = ref("");
const messages = ref([
  { sender: "System", text: "ChatGPT와 대화를 시작해보세요!" },
]);
const titleType = ref([]);

// // 컴포넌트 마운트 시 데이터 가져오기
onMounted(() => { });
const onClickTodayMenu = async () => {
  const cardContainer = document.getElementById("cardContainer");
  const cardItemItem = cardContainer.getElementsByClassName("card-text");
  const titleType = ref([]);

  Array.from(cardItemItem).forEach((item) => {
    const parts = item.innerHTML.split("<br>");
    const parser = new DOMParser();
    const doc = parser.parseFromString(parts[0], "text/html");
    const decodedString = doc.documentElement.textContent;
    titleType.value.push(decodedString);
  });

  const askQ = titleType.value.join(", ");
  userMessage.value = `오늘의 메뉴 ${askQ} 중에서 오늘의 메뉴를 추천해줘 :)`;

  // sendMessage 함수 호출하여 메시지 전송
  await sendMessage();
};

const sendMessage = async () => {
  if (userMessage.value.trim() === "") return;

  // 사용자 메시지 추가
  messages.value.push({ sender: "User", text: userMessage.value });

  try {
    const response = await axios.post(
      "https://api.openai.com/v1/chat/completions",
      {
        model: "gpt-3.5-turbo",
        messages: [{ role: "user", content: userMessage.value }],
        max_tokens: 150,
      },
      {
        headers: {
          "Content-Type": "application/json",
     //     Authorization: ``,

        },
      }
    );

    // 챗봇의 응답이 비어있지 않은 경우에만 추가
    if (response.data.choices[0].message.content.trim() !== "") {
      messages.value.push({
        sender: "ChatGPT",
        text: response.data.choices[0].message.content,
      });
    }
  } catch (error) {
    console.error("Error:", error);
    messages.value.push({
      sender: "System",
      text: "오류가 발생했습니다. 다시 시도해 주세요.",
    });
  } finally {
    userMessage.value = "";
  }
};

// 디바운스된 sendMessage 함수
const debouncedSendMessage = debounce(sendMessage, 300);
</script>

<style scoped>
.chat-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.chat-box {
  height: 250px;
  /* overflow-y: auto; */
  margin-bottom: 20px;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 10px;
}

.message {
  margin-bottom: 10px;
}

.input-box {
  display: flex;
  max-width: 600px;
}

.input-box input {
  /* flex: 1; */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.input-box button {
  padding: 10px 20px;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.input-box2 button {
  padding: 10px 20px;
  border: none;
  background-color: #1f2937;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.input-box2 button:hover {
  opacity: 0.7;
}

.input-box button:hover {
  background-color: #0056b3;
}
</style>
