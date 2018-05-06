# MagicTooltipView

![Magic tooltip popup window demo](https://github.com/filippella/MagicTooltipView/blob/master/screenshots/tooltip-demo.png)

How to use?

```java
package org.dalol.demo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.dalol.magictooltipview.MagicTooltipView
import org.dalol.magictooltipview.TooltipPopup

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View?) {
        val popup = TooltipPopup(this)
        val tooltipView = MagicTooltipView(this)
        tooltipView.setTooltipBgColor(Color.parseColor("#7e4add"))
        tooltipView.setText("Filippo tooltip message", 14f, false)
        popup.setView(tooltipView)
        popup.setFocusable(true)
        popup.show(v)
    }
}

```

License
-------

    Copyright 2018 Filippo Engidashet.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
