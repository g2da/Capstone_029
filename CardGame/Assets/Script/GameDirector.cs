using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameDirector : MonoBehaviour
{

    Sprite[] check_card = new Sprite[2];
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
        if(f_c == 2 && Input.GetMouseButtonDown(0))
        {
            
        }
    }

    public void selected_Card(Sprite worldImage) //이 함수는 카드를 클릭하면 호출된다. 호출되었을 때 클릭한 카드를 배열에 저장한다.
        //배열에 클릭된 카드 2장을 저장하는 함수
    {
        if (check_card[0] == null)
        {
            check_card[0] = worldImage;
        }
        else if (check_card[1] == null)
        {
            check_card[1] = worldImage;
        }
        
    }


}
