<script setup lang="ts">

import type { Patient } from '~/types'

definePageMeta({
  middleware: ["user-only"],
});


const route = useRoute()
console.log(route.params) 

</script>



<template>
    <UDashboardPage>
      <UDashboardPanel grow>
        <UDashboardNavbar title="Realtime biometric monitoring" />
        
        <UDashboardPanelContent>
          <StreamingWebsocket v-bind:patient_id = $route.params.id />            
      </UDashboardPanelContent>

      </UDashboardPanel>
    </UDashboardPage>
    
</template>