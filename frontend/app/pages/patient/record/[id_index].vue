<script setup lang="ts">

import type { Patient , AbnormalHealthRecord} from '~/types'

definePageMeta({
  middleware: ["user-only"],
});


const route = useRoute()
console.log(route.params) 

const defaultColumns = [{
  key: 'citizenid',
  label: 'citizen ID',
  initialCache: false
}, {
  key: 'timestamp',
  label: 'Timestamp',
  initialCache: false
},{
  key: 'comment',
  label: 'Comment',
  initialCache: false
}]



const selectedColumns = ref(defaultColumns)
const columns = computed(() => defaultColumns.filter(column => selectedColumns.value.includes(column)))
const sort = ref({ column: 'timestamp', direction: 'asc' as const })
const query = computed(() => ({}))
const {data: abnormalHealthRecord} = await useFetch<AbnormalHealthRecord[]>(`http://localhost:8080/patient/${route.params.id_index}/allRecords`, { query, default: () => [] })



function onSelect(row: AbnormalHealthRecord) {  
  navigateTo({ path: `/patient/record/${route.params.id_index}/${row.timestamp}`})
}


function buttonOnClick(){
  navigateTo({ path: `/patient/realtime/${route.params.id_index}`})
}

</script>

<template>
    <UDashboardPage>
      <UDashboardPanel grow>
        <UDashboardNavbar title="Patient's records" />

        <UDashboardPanelContent>

          <PatientMedicalHistory v-bind:patient_id = $route.params.id_index></PatientMedicalHistory>

          <div>
             <UButton :ui="{ rounded: 'rounded-full' }">
               <NuxtLink @click.native='buttonOnClick()'> Real Time Monitoring </NuxtLink>
             </UButton>
          </div>          

          <UTable
            v-model:sort="sort"
            :rows="abnormalHealthRecord"
            :columns="columns"
            sort-mode="manual"
            class="w-full"
            :ui="{ divide: 'divide-gray-200 dark:divide-gray-800' }"
            @select="onSelect"
          >
          </UTable>

      </UDashboardPanelContent>

      </UDashboardPanel>
    </UDashboardPage>
    
</template>