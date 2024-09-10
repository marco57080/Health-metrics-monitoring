<script setup lang="ts">
const route = useRoute()
const appConfig = useAppConfig()

const links = [{
  id: 'home',
  label: 'Home',
  icon: 'i-heroicons-home',
  to: '/',
  tooltip: {
    text: 'Home',
    shortcuts: ['G', 'H']
  }
},
{
  id: 'patients',
  label: 'Patients',
  icon: 'i-heroicons-user-group',
  to: '/patients',
  tooltip: {
    text: 'Patients',
  }
}]

</script>

<template>
  <UDashboardLayout>
    <UDashboardPanel
      :width="250"
      :resizable="{ min: 200, max: 300 }"
      collapsible
    >

      <UDashboardSidebar>
        <UDashboardSidebarLinks :links="links" />

        <UDivider />

        <div class="flex-1" />

        <UDivider class="sticky bottom-0" />

        <template #footer>      
          <UserDropdown />
        </template>
      </UDashboardSidebar>
    </UDashboardPanel>

    <slot />


  </UDashboardLayout>
</template>
