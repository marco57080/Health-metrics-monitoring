<script lang="ts" setup>
import type { Patient, User } from '~/types'

definePageMeta({
  middleware: ["user-only"],
});


const defaultColumns = [{
  key: 'citizenID',
  label: 'citizen ID',
  sortable: true,
  initialCache: false
}, {
  key: 'fullName',
  label: 'full Name',
  sortable: true,
  initialCache: false
}]


const selectedColumns = ref(defaultColumns)
const sort = ref({ column: 'citizenID', direction: 'asc' as const })
const columns = computed(() => defaultColumns.filter(column => selectedColumns.value.includes(column)))
const query = computed(() => ({}))
const { data: patients, pending } = await useFetch<Patient[]>('http://localhost:8080/patient', { query, default: () => [] })


function onSelect(row: Patient) {
  navigateTo({ path: `/patient/record/${row.citizenID}` })
}


</script>

<template>
  <UDashboardPage>
    <UDashboardPanel grow>
      <UDashboardNavbar
        title="Patients"
        :badge="patients.length"
      >
      </UDashboardNavbar>


      <UTable
        v-model:sort="sort"
        :rows="patients"
        :columns="columns"
        sort-mode="manual"
        class="w-full"
        :ui="{ divide: 'divide-gray-200 dark:divide-gray-800' }"
        @select="onSelect"
      >
      </UTable>

    </UDashboardPanel>
  </UDashboardPage>
</template>
