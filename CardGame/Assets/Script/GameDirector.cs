using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameDirector : MonoBehaviour
{
    [SerializeField]
    public int f_c = 0; //flip_count 뒤집어진 횟수
    // Start is called before the first frame update
    void Start()
    {
        
    }

    public void Flip_Count()
    {
        f_c += 1;
    }

    // Update is called once per frame
    void Update()
    {
        if(f_c == 2)
        {
            
        }
    }
}
