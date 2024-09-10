<template>
    <div> <p>Patient's citizen ID: {{ patient_id }}</p> </div>

    <div id="metric">
    <p id ="timestamp">Time:</p>
    <p id ="heart_rate_average">Average Heart Rate:</p>
    <p id ="spo2_average">Average Spo2:</p>
    <p id ="blood_pressure_average">Average Blood Pressure:</p>
  </div>

</template>

<script>
export default {
    props:{
        patient_id: {
            required: true,
        }, 
        timestamp: {
            required: true,
        }
    },
    data(){
        return{
            client_id: this.patient_id,
            heart_rate_average: 0,
            spo2_average: 0,
            blood_pressure_systolic_average: 0,
            blood_pressure_diastolic_average: 0,            
        }
    },
    mounted(){
        this.patient_id; 
        this.timestamp;
    },
    async created(){        
        let url = `http://localhost:8080/patient/record/${this.patient_id}/${this.timestamp}`;
        const response = await fetch(url)
        const data = await response.json()

        console.log(data);


        this.heart_rate_average = data.heartrateaverage;
        this.spo2_average = data.oxygensaturationaverage;
        this.blood_pressure_systolic_average= data.bloodpressuresystolicaverage;
        this.blood_pressure_diastolic_average= data.bloodpressurediastolicaverage;
        this.initMetricField();
    },
    methods:{
        initMetricField(){

            let date_Locale = new Date(this.timestamp).toLocaleDateString("zh-TW");
            let time_Locale = new Date(this.timestamp).toLocaleTimeString("zh-TW");

            document.getElementById('timestamp').textContent = "Time: " + date_Locale + " " + time_Locale ;
            document.getElementById('heart_rate_average').textContent = "Heart rate average: " + this.heart_rate_average;
            document.getElementById('spo2_average').textContent = "Spo2 average: " + this.spo2_average;
            document.getElementById('blood_pressure_average').textContent = "Average Blood Pressure: " + this.blood_pressure_diastolic_average + " | " + this.blood_pressure_systolic_average;

        }
    }
}
</script>




