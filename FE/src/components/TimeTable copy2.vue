<template>
  <div class="">
    <div class="">
      <h2>
        <!-- 고객님의 점심시간은 {{ upfloorKey }}층 |
        {{ times[findTime(upfloorKey)] }} 입니다.  -->
        이번달 층별 점심시간 입니다.
      </h2>
      <!-- <h2>
        고객님의 점심시간은 {{ upfloorKey }}층 |
        {{ times[findTime(upfloorKey)] }} 입니다.
      </h2> -->
      <div>
        <div>
          <section>
            <div class="flex flex-col undefined">
              <button type="button"
                class="relative after:absolute after:pointer-events-none after:w-full after:h-full after:opacity-0 after:left-0 after:top-0 after:content-[&quot;&quot;] after:bg-[black] after:rounded-[inherit] after:transition-opacity after:duration-100 after:delay-0 active:after:opacity-[0.08] flex items-center css-0">
                <div class="w-full css-17ygvfo e1yk5yoz0">
                  <div class="css-yeouz0 exmr6sa4">
                    <div class="css-1w9ugql exmr6sa3">
                      <div class="opacity-100 css-lyel5l eg0eqjh1">
                        <!-- <picture class="dark:hidden"
                          ><img
                            alt="사망"
                            loading="lazy"
                            width="34"
                            height="34"
                            decoding="async"
                            data-nimg="1"
                            class="pointer-events-none invisible select-none"
                            srcset="
                              https://cdn.banksalad.com/cdn-cgi/image/width=48,metadata=none,format=webp/graphic/color/icon/heart-broken-orchid/original.png 1x,
                              https://cdn.banksalad.com/cdn-cgi/image/width=96,metadata=none,format=webp/graphic/color/icon/heart-broken-orchid/original.png 2x
                            "
                            src="https://cdn.banksalad.com/cdn-cgi/image/width=96,metadata=none,format=webp/graphic/color/icon/heart-broken-orchid/original.png"
                            style="
                              color: transparent;
                              width: 34px;
                              height: 34px;
                              visibility: visible;
                            " /></picture
                        ><picture class="hidden dark:block"
                          ><img
                            alt="사망"
                            loading="lazy"
                            width="34"
                            height="34"
                            decoding="async"
                            data-nimg="1"
                            class="pointer-events-none invisible select-none"
                            srcset="
                              https://cdn.banksalad.com/cdn-cgi/image/width=48,metadata=none,format=webp/graphic/color/icon/heart-broken-orchid/original-darkmode.png 1x,
                              https://cdn.banksalad.com/cdn-cgi/image/width=96,metadata=none,format=webp/graphic/color/icon/heart-broken-orchid/original-darkmode.png 2x
                            "
                            src="https://cdn.banksalad.com/cdn-cgi/image/width=96,metadata=none,format=webp/graphic/color/icon/heart-broken-orchid/original-darkmode.png"
                            style="
                              color: transparent;
                              width: 34px;
                              height: 34px;
                            "
                        /></picture> -->
                      </div>
                    </div>
                    <div class="css-8misg8 exmr6sa2">
                      <div class="text-gray-050 font-bold opacity-100 text-body-15 css-1h52dri exmr6sa1">
                        사망
                      </div>
                      <div class="text-gray-500 font-regular opacity-100 text-caption-12 css-1h52dri exmr6sa0">
                        3개 중 3개 항목 부족
                      </div>
                    </div>
                  </div>
                  <div
                    class="text-white-900 text-opacity-100 bg-red-100 bg-opacity-100 font-medium css-ym0gvz e1yxy0680">
                    부족
                  </div>
                </div>
                <div class="w-24 h-auto flex justify-center" style="
                    --tw-rotate: 0deg;
                    transform: translate(
                        var(--tw-translate-x),
                        var(--tw-translate-y)
                      )
                      rotate(var(--tw-rotate)) skewX(var(--tw-skew-x))
                      skewY(var(--tw-skew-y)) scaleX(var(--tw-scale-x))
                      scaleY(var(--tw-scale-y));
                  ">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" class="text-gray-500" aria-label="접기">
                    <title id="BdsMonoCommonArrowUpLarge1Svg-title">접기</title>
                    <path fill-rule="evenodd" clip-rule="evenodd"
                      d="m12 10.44 5.035 5.06.965-.97-6-6.03-6 6.03.966.97L12 10.44Z" fill="currentColor"></path>
                  </svg>
                </div>
              </button>
              <div class="overflow-hidden" style="height: auto">
                <article class="ml-42">
                  <section class="rounded-12 bg-gray-800 py-8">
                    <div class="bg-transparent css-35vo4m ecu85n66">
                      <div class="css-ikk7ay ecu85n65">
                        <span class="text-gray-500 opacity-100 font-regular css-cvvv5h ecu85n63">분류</span><span
                          class="text-gray-500 opacity-100 font-regular css-1prqhvw ecu85n62">적정 금액</span><span
                          class="text-gray-500 opacity-100 font-regular css-ycf5c8 ecu85n61">내 보험금</span>
                      </div>
                    </div>
                    <div class="flex items-center justify-between px-20 py-4">
                      <div class="break-all text-body-14">질병 사망</div>
                      <div class="flex">
                        <div
                          class="text-gray-400 text-opacity-100 bg-gray-700 bg-opacity-100 font-medium css-bafq82 e1yxy0680">
                          5,000만원
                        </div>
                        <div
                          class="text-red-100 text-opacity-100 bg-pink-100 bg-opacity-15 font-bold css-yn3wtu e1yxy0680">
                          0원
                        </div>
                      </div>
                    </div>
                    <div class="flex items-center justify-between px-20 py-4">
                      <div class="break-all text-body-14">암 사망</div>
                      <div class="flex">
                        <div
                          class="text-gray-400 text-opacity-100 bg-gray-700 bg-opacity-100 font-medium css-bafq82 e1yxy0680">
                          2억원
                        </div>
                        <div
                          class="text-red-100 text-opacity-100 bg-pink-100 bg-opacity-15 font-bold css-yn3wtu e1yxy0680">
                          0원
                        </div>
                      </div>
                    </div>
                    <div class="flex items-center justify-between px-20 py-4">
                      <div class="break-all text-body-14">상해 사망</div>
                      <div class="flex">
                        <div
                          class="text-gray-400 text-opacity-100 bg-gray-700 bg-opacity-100 font-medium css-bafq82 e1yxy0680">
                          1억원
                        </div>
                        <div
                          class="text-red-100 text-opacity-100 bg-pink-100 bg-opacity-15 font-bold css-yn3wtu e1yxy0680">
                          0원
                        </div>
                      </div>
                    </div>
                  </section>
                  <div class="bg-transparent h-8" height="8"></div>
                  <p class="text-caption-12 text-gray-500"></p>
                  <div class="bg-transparent h-8" height="8"></div>
                </article>
              </div>
            </div>
          </section>
          <!-- <table class="w-[400px]">
            <thead class="">
              <tr class="text-center">
                <th class="border">Floor</th>
                <th class="border">Time</th>
              </tr>
            </thead>
            <tr
              :style="getTableHeaderStyle()"
              v-for="floor in floors"
              :key="floor.key"
            >
              <td>{{ floor.key }} 층</td>
              <td>
                {{ times[floor.index] }}
              </td>
            </tr>
          </table> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from "vue";
const startMonth = 1;
let lastMonth = new Date().getMonth() + 1;

const printOut = (arr) => {
  arr.value.map((e) => console.log(e));
};
const times = ref([
  "12 : 00 - 13 : 00",
  "12 : 15 - 13 : 15",
  "12 : 30 - 13 : 30",
  "12 : 45 - 13 : 45",
  "13 : 00 - 14 : 00",
]);
const floors = ref([
  { key: 5, index: 0 },
  { key: 3, index: 1 },
  { key: 2, index: 2 },
  { key: 7, index: 3 },
  { key: 6, index: 4 },
]);

const findTime = (index) => {
  let val = 0;
  floors.value.map((each) =>
    each.key == index ? (val = each.index) : (val = val)
  );
  return val;
};
const num = floors.value.length;

let floorrrr = ref(0); //내가 속한 층의 인덱스
let upfloorKey = ref(3); // <<< 여기로 회원 층 정보 주기

const upfloor = () => {
  floorrrr.value = (floorrrr.value + 1) % floors.value.length;

  // console.log(floors)
  for (let i = 0; i < floors.value.length; i++) {
    let floor = floors.value[i];
    if (floor.index === floorrrr.value) {
      upfloorKey.value = floor.key;
      console.log(floor.key);
    }
  }
};
let floorrrrTime = computed(() => {
  return floors.value.findIndex((floor) => floor.key === floorrrr.value);
});
onMounted(() => {
  let diff = lastMonth - startMonth;
  floors.value.map((each) => {
    each.index += diff;
    each.index = each.index % num;
  });

  floors.value = floors.value.sort((a, b) => a.index - b.index);
  // printOut(floors)
});

// 기존 Script
const getTableHeaderStyle = () => {
  return {
    textAlign: "center",
    border: "1px solid #ccc",
  };
};

const getCellStyle = (isHeader) => {
  const style = {
    textAlign: "center",
    border: "1px solid #ccc",
  };

  if (isHeader) {
    style.fontWeight = "bold";
  }

  return style;
};

const selectedFloorStyle = {
  backgroundColor: "#E7F1FF",
  border: "1px solid red",
  textAlign: "center",
};

const selectedTimeStyle = {
  border: "1px solid red",
  textAlign: "center",
};
</script>

<style scoped></style>
