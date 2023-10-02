export default function taskReducer(tasks, action){
    switch (action.type) {
        case "added":{
            return[...tasks, {
                // id: action.id,
                text: action.text,
                done: false
            }]
        }
        default: {
            throw Error("Uknown action:" + action.type)
        }
    }
}