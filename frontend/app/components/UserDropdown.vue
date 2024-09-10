<script setup lang="ts">
const { isDashboardSearchModalOpen } = useUIState()
const { metaSymbol } = useShortcuts()


// auth related
const currentUser = useAuthUser();
const isAdmin = useAdmin();
const { logout } = useAuth();

const form = reactive({
  pending: false,
});

async function onLogoutClick() {
  try {
    form.pending = true;

    await logout();

    await navigateTo("/");
  } catch (error) {
    console.error(error);
  } finally {
    form.pending = false;
  }
}

const items = computed(() => [
  [{
    slot: 'account',
    label: '',
    disabled: true
  }],
  [{
    label: 'Sign out',
    icon: 'i-heroicons-arrow-left-on-rectangle',
    click: () => {
      onLogoutClick()
    }
  }]
])
</script>

<template>
  
  <UDropdown
    mode="hover"
    :items="items"
    :ui="{ width: 'w-full', item: { disabled: 'cursor-text select-text' } }"
    :popper="{ strategy: 'absolute', placement: 'top' }"
    class="w-full"
  >
    <template #default="{ open }">
      <UButton
        color="gray"
        variant="ghost"
        class="w-full"
        label="User"
        :class="[open && 'bg-gray-50 dark:bg-gray-800']"
      >

        <template #trailing>
          <UIcon
            name="i-heroicons-ellipsis-vertical"
            class="w-5 h-5 ml-auto"
          />
        </template>
      </UButton>
    </template>

  </UDropdown>
</template>
