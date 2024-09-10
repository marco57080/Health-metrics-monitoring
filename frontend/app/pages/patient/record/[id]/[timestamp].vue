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
        <UDashboardNavbar title="Patient's abnormal record" />
        
        <UDashboardPanelContent>
          <BoundedDataChart v-bind:patient_id = $route.params.id v-bind:timestamp = Number($route.params.timestamp)></BoundedDataChart>
          <PatientMedicalRecords v-bind:patient_id = $route.params.id v-bind:timestamp = Number($route.params.timestamp)> </PatientMedicalRecords>
        </UDashboardPanelContent>

      </UDashboardPanel>
    </UDashboardPage>
    
</template>