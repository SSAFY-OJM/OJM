<template>
  <Disclosure as="nav" class="bg-gray-800" v-slot="{ open }">
    <div class="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8 min-w-[360px]">
      <div class="relative flex h-16 items-center justify-between">
        <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
          <DisclosureButton
            class="relative inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
          >
            <span class="absolute -inset-0.5" />
            <span class="sr-only">Open main menu</span>
            <Bars3Icon v-if="!open" class="block h-6 w-6" aria-hidden="true" />
            <XMarkIcon v-else class="block h-6 w-6" aria-hidden="true" />
          </DisclosureButton>
        </div>
        <div
          class="hidden min-[640px]:flex flex-1 items-center justify-center sm:justify-start text-white min-w-[100px]"
        >
          <div class="flex justify-center items-center gap-2" @click="goToHome">
            <img class="color w-8 h-8" src="../assets/images/icon.png" />
            <div class="text-white text-sm">뭐먹지</div>
          </div>
        </div>
        <div
          v-if="!type && token"
          class="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start"
        >
          <div class="hidden sm:block">
            <div class="flex space-x-4 w-[300px]">
              <a
                v-for="item in navigation2"
                :key="item.name"
                @click.prevent="item.route"
                :class="[
                  item.href === routerRef
                    ? 'bg-gray-800 text-white'
                    : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                  'rounded-md px-3 py-2 text-sm font-medium no-underline',
                ]"
                :aria-current="item.current ? 'page' : undefined"
              >
                {{ item.name }}
              </a>
            </div>
          </div>
        </div>
        <div
          v-if="type && token"
          class="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start"
        >
          <div class="hidden sm:block">
            <div class="flex space-x-4 w-[300px]">
              <a
                v-for="item in navigation"
                :key="item.name"
                @click.prevent="item.route"
                :class="[
                  item.href === routerRef
                    ? 'bg-gray-800 text-white'
                    : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                  'rounded-md px-3 py-2 text-sm font-medium no-underline',
                ]"
                :aria-current="item.current ? 'page' : undefined"
              >
                {{ item.name }}
              </a>
            </div>
          </div>
        </div>
        <div
          class="inset-y-0 right-0 flex w-full justify-between items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0"
        >
          <template v-if="token">
            <Menu
              as="div"
              class="relative ml-3 text-white sm:justify-center sm:items-center w-full"
            >
              <div
                class="flex gap-2 justify-center items-center sm:w-full md:justify-end lg:justify-end"
              >
                <span class="text-xs mt-2"> 반갑습니다. </span>
                <MenuButton
                  class="relative flex rounded-full bg-gray-800 text-sm"
                >
                  <span class="absolute -inset-1.5" />
                  <span class="sr-only">Open user menu</span>
                  <span class="userNameUL">{{ userName }} 님</span>
                </MenuButton>
              </div>
              <transition
                enter-active-class="transition ease-out duration-100"
                enter-from-class="transform opacity-0 scale-95"
                enter-to-class="transform opacity-100 scale-100"
                leave-active-class="transition ease-in duration-75"
                leave-from-class="transform opacity-100 scale-100"
                leave-to-class="transform opacity-0 scale-95"
              >
                <MenuItems
                  class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
                >
                  <MenuItem v-slot="{ active }" @click="logout">
                    <a
                      href="#"
                      :class="[
                        active ? 'bg-gray-100' : '',
                        'block px-4 py-2 text-sm text-gray-700',
                      ]"
                      >Sign out</a
                    >
                  </MenuItem>
                </MenuItems>
              </transition>
            </Menu>
          </template>
          <template v-else>
            <div class="w-full text-white flex gap-4 justify-end">
              <div class="cursor-pointer" @click="login">Login</div>
              <div class="cursor-pointer" @click="join">Join</div>
            </div>
          </template>
        </div>
      </div>
    </div>

    <DisclosurePanel class="sm:hidden">
      <div class="space-y-1 px-2 pb-3 pt-2">
        <DisclosureButton
          v-for="item in navigation"
          :key="item.name"
          as="a"
          @click.prevent="item.route"
          :class="[
            item.href === routerRef
              ? 'bg-gray-900 text-white'
              : 'text-gray-300 hover:bg-gray-700 hover:text-white',
            'block rounded-md px-3 py-2 text-base font-medium',
          ]"
          :aria-current="item.current ? 'page' : undefined"
          >{{ item.name }}</DisclosureButton
        >
      </div>
    </DisclosurePanel>
  </Disclosure>
</template>

<script setup>
import {
  Disclosure,
  DisclosureButton,
  DisclosurePanel,
  Menu,
  MenuButton,
  MenuItem,
  MenuItems,
} from "@headlessui/vue";
import { Bars3Icon, BellIcon, XMarkIcon } from "@heroicons/vue/24/outline";
import { useRouter } from "vue-router";
import AdminMenu from "@/components/AdminMenu.vue";
import { onMounted, computed, ref } from "vue";
import { useStore } from "vuex";
const router = useRouter();
const routerRef = ref("Home");
const store = useStore();

const token = computed(() => store.getters.token);
const type = computed(() => store.getters.userType);
const userName = computed(() => store.getters.userName);
if (userName.value == undefined) {
  userName.value = "";
}

const routerFn = (route) => {
  routerRef.value = route;
  router.push({
    path: route,
  });
};
const logout = () => {
  store.dispatch("logout");
  window.location.reload();
  router.push("/");
};
const login = () => {
  router.push("/login");
};
const goToHome = () => {
  router.push("/");
};
const join = () => {
  router.push("/join");
};

const navigation = [
  {
    name: "피드백 보기",
    href: "/admin/feedback/list",
    current: false,
    route: () => routerFn("/admin/feedback/list"),
  },
  {
    name: "글쓴목록 보기",
    href: "/admin/board/list",
    current: false,
    route: () => routerFn("/admin/board/list"),
  },
  {
    name: "글쓰기",
    href: "/admin/board/write",
    current: false,
    route: () => routerFn("/admin/board/write"),
  },
];
const navigation2 = [
  {
    name: "나의 피드백 보기",
    href: "/feedback/list/:userId",
    current: false,
    route: () => routerFn("/feedback/list/:userId"),
  },
];
</script>

<style scoped>
.userNameUL::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: -0.2rem;
  height: 1px;
  width: calc(100%);
  background: rgba(49, 130, 246, 0.8);
}
</style>
