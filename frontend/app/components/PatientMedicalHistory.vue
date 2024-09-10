<template>

    <div id="metric">
        <p id ="fullName">Full Name: </p>
        <p id ="citizenid">Citizen ID: </p>    
        <p id ="age">Age: </p>
        <p id ="sex">Sex:</p>
        <p id ="allergy">Allergy:</p>
        <p id ="illness">Illness:</p>
    </div>

</template>

<script>
export default {
    props:{
        patient_id: {
            required: true,
        },
    },
    data(){
        return{
            citizenid: this.patient_id,
            fullName: null,
            age: null, 
            sex: null, 
            allergy: [], 
            illness:[]
        }
    },
    mounted(){
        this.patient_id; 
    },
    async created(){        
        console.log(this.patient_id);
        let url = `http://localhost:8080/patient/medicalHistory/${this.patient_id}`;
        const response = await fetch(url);
        const data = await response.json();
        this.fullName = data.fullname;
        this.age = data.age; 
        this.sex = data.sex; 
        this.allergy = data.allergy; 
        this.illness = data.illness;
        this.initMetricField();
    },
    methods:{
        initMetricField(){
            document.getElementById('fullName').textContent = "Full Name: " + this.fullName;
            document.getElementById('citizenid').textContent = "Citizen ID: " + this.citizenid;
            document.getElementById('age').textContent = "Age: " + this.age;
            document.getElementById('sex').textContent = "Sex: " + this.sex;
            document.getElementById('allergy').textContent = "Allergy: " + this.allergy;
            document.getElementById('illness').textContent = "Illness: " + this.illness;
        }
    }
}
</script>




