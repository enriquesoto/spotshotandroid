package com.aqpup.waitforit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

class TestFragmentAdapter extends FragmentStatePagerAdapter  {
	 
//	 private String messagesTitle[];
	private int imessagesTitle[];
	private String messagesContent[];

	 private int icons[];
	
	
	 public TestFragmentAdapter(FragmentManager fm, int imessagesTitle[], String messagesContent[], int icons[]) {
         super(fm);
         this.imessagesTitle = imessagesTitle;
         this.messagesContent = messagesContent;
         this.icons = icons;

     }

     @Override
     public Fragment getItem(int i) {
         Fragment fragment=null;
         if(i==0)
         {
             fragment=new TestFragment(icons[0],imessagesTitle[0], messagesContent[0], 3);
         }
         if(i==1)
         {
             fragment=new TestFragment(icons[1],imessagesTitle[1], messagesContent[1], 2);
         }
         if(i==2)
         {
             fragment=new TestFragment(icons[2],imessagesTitle[2],messagesContent[2], 1);
         }
         if(i==3)
         {
             fragment=new TestFragment(icons[3],imessagesTitle[3],messagesContent[3], 0);
         }
         return fragment;
     }

     @Override
	
    public int getCount() {
        return PresentationActivity.numberOfViewPagerChildren;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
    	
    	TestFragment testFragment = (TestFragment)object;
    	 
    	if(testFragment.id == 3){
             view.setTag(3);
         }
        if(testFragment.id == 2){
            view.setTag(2);
        }
        if(testFragment.id == 1){
            view.setTag(1);
        }
        if(testFragment.id == 0){
            view.setTag(0);
        }
        
        return super.isViewFromObject(view, object);
    } 

}